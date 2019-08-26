package hu.naturlecso.pdpd.data.store

import hu.naturlecso.pdpd.data.cache.ContactDetailsDataModel
import hu.naturlecso.pdpd.data.cache.PersonDao
import hu.naturlecso.pdpd.data.cache.PersonDataModel
import hu.naturlecso.pdpd.domain.model.ContactDetails
import hu.naturlecso.pdpd.domain.model.ContactDetailsType
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
                .flatMap { personData -> personDao.getContactDetailsByPerson(personData.id)
                    .take(1)
                    .map { contactDetailsData -> personData.apply { contactDetails = contactDetailsData } }
                    .map { mapPersonDataModelToDomainModel(it) } }
                .toList()
        }
        .map { it.apply { sortBy { person -> person.id } } }
        .distinctUntilChanged()

    override fun get(id: Int): Flowable<Person> = Flowables.combineLatest(
        personDao.get(id),
        personDao.getContactDetailsByPerson(id)
    ) { personData, contactDetailsData -> personData.apply { contactDetails = contactDetailsData } }
        .map { mapPersonDataModelToDomainModel(it) }
        .subscribeOn(Schedulers.io())

    private fun mapPersonDataModelToDomainModel(dataModel: PersonDataModel) =
        Person(
            id = dataModel.id,
            name = dataModel.name,
            owner = Owner(dataModel.ownerName, dataModel.ownerEmail),
            organization = mapOrganization(dataModel),
            contactDetails = dataModel.contactDetails?.map { mapContactDetailsDataModelToDomainModel(it) },
            openDealsCount = dataModel.openDealsCount,
            closedDealsCount = dataModel.closedDealsCount,
            wonDealsCount = dataModel.wonDealsCount,
            lostDealCount = dataModel.lostDealsCount
        )

    private fun mapContactDetailsDataModelToDomainModel(dataModel: ContactDetailsDataModel) =
        ContactDetails(
            type = ContactDetailsType.valueOf(dataModel.type.name),
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
