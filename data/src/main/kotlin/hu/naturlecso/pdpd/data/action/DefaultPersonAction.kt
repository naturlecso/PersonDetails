package hu.naturlecso.pdpd.data.action

import hu.naturlecso.pdpd.data.BuildConfig
import hu.naturlecso.pdpd.data.api.ContactApiModel
import hu.naturlecso.pdpd.data.api.PersonApiModel
import hu.naturlecso.pdpd.data.api.PipedrivePersonsApiService
import hu.naturlecso.pdpd.data.cache.ContactDataModel
import hu.naturlecso.pdpd.data.cache.ContactTypeDataModel
import hu.naturlecso.pdpd.data.cache.PersonDao
import hu.naturlecso.pdpd.data.cache.PersonDataModel
import hu.naturlecso.pdpd.domain.action.PersonAction
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.UUID

class DefaultPersonAction(
    private val pipedrivePersonsApiService: PipedrivePersonsApiService,
    private val personDao: PersonDao
) : PersonAction {

    override fun refresh(): Completable {
        return pipedrivePersonsApiService.allPersons(BuildConfig.PIPEDRIVE_API_KEY)
            .map { it.data }
            .map { it.map { person -> mapPersonApiModelToDataModel(person) } }
            .flatMapCompletable { Completable.fromAction { personDao.replaceAll(it) } }
            .subscribeOn(Schedulers.io())
    }

    private fun mapPersonApiModelToDataModel(apiModel: PersonApiModel) = PersonDataModel(
            id = apiModel.id,
            name = apiModel.name,
            ownerName = apiModel.owner.name,
            ownerEmail = apiModel.owner.email,
            organizationName = apiModel.organization?.name,
            organizationAddress = apiModel.organization?.address,
            openDealsCount = apiModel.openDealsCount,
            closedDealsCount = apiModel.closedDealsCount,
            wonDealsCount = apiModel.wonDealsCount,
            lostDealsCount = apiModel.lostDealsCount
        ).apply { contacts = mapPhones(apiModel) + mapEmails(apiModel) }

    private fun mapContactApiModelToDataModel(
        apiModel: ContactApiModel,
        personId: Int,
        type: ContactTypeDataModel) =
        ContactDataModel(
            id = UUID.randomUUID().toString(),
            type = type,
            label = apiModel.label,
            value = apiModel.value,
            primary = apiModel.primary,
            personId = personId
        )

    private fun mapPhones(apiModel: PersonApiModel): List<ContactDataModel> {
        return apiModel.phone
            .filter { contact -> contact.value.isNotEmpty() }
            .map { contact -> mapContactApiModelToDataModel(
                contact, apiModel.id, ContactTypeDataModel.PHONE) }
    }

    private fun mapEmails(apiModel: PersonApiModel): List<ContactDataModel> {
        return apiModel.email
            .filter { contact -> contact.value.isNotEmpty() }
            .map { contact -> mapContactApiModelToDataModel(
                contact, apiModel.id, ContactTypeDataModel.EMAIL) }
    }
}
