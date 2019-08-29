package hu.naturlecso.pdpd.data.store

import hu.naturlecso.pdpd.data.cache.ContactDataModel
import hu.naturlecso.pdpd.data.cache.PersonDao
import hu.naturlecso.pdpd.data.cache.PersonDataModel
import hu.naturlecso.pdpd.domain.model.Contact
import hu.naturlecso.pdpd.domain.model.ContactType
import hu.naturlecso.pdpd.domain.model.Organization
import hu.naturlecso.pdpd.domain.model.Owner
import hu.naturlecso.pdpd.domain.model.Person
import hu.naturlecso.pdpd.domain.store.PersonStore
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.schedulers.Schedulers

class DefaultPersonStore(
    private val personDao: PersonDao
) : PersonStore {

    override fun getList(): Flowable<List<Person>> = personDao.getAll()
        .flatMapSingle { personList ->
            Flowable.fromIterable(personList)
                .flatMap { personDataModel -> personDao.getContactsByPerson(personDataModel.id)
                    .take(1)
                    .map { contactDataModel -> personDataModel.apply { contacts = contactDataModel } }
                    .map { mapPersonDataModelToDomainModel(it) } }
                .toList()
        }
        .map { it.apply {
            sortBy { person -> person.name }
            sortByDescending { person -> person.openDealsCount } }
        }
        .distinctUntilChanged()

    override fun get(id: Int): Flowable<Person> = Flowables.combineLatest(
        personDao.get(id),
        personDao.getContactsByPerson(id)
    ) { personDataModel, contactDataModel -> personDataModel.apply { contacts = contactDataModel } }
        .map { mapPersonDataModelToDomainModel(it) }
        .subscribeOn(Schedulers.io())

    private fun mapPersonDataModelToDomainModel(dataModel: PersonDataModel) =
        Person(
            id = dataModel.id,
            name = dataModel.name,
            owner = Owner(dataModel.ownerName, dataModel.ownerEmail),
            organization = mapOrganization(dataModel),
            contact = dataModel.contacts.map { mapContactDataModelToDomainModel(it) },
            openDealsCount = dataModel.openDealsCount,
            closedDealsCount = dataModel.closedDealsCount,
            wonDealsCount = dataModel.wonDealsCount,
            lostDealsCount = dataModel.lostDealsCount
        )

    private fun mapContactDataModelToDomainModel(dataModel: ContactDataModel) =
        Contact(
            type = ContactType.valueOf(dataModel.type.name),
            label = dataModel.label,
            value = dataModel.value,
            primary = dataModel.primary
        )

    private fun mapOrganization(dataModel: PersonDataModel) = if (dataModel.organizationName == null) {
        null
    } else {
        Organization(dataModel.organizationName, dataModel.organizationAddress)
    }
}
