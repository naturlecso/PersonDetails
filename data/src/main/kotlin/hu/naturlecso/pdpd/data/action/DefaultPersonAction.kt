package hu.naturlecso.pdpd.data.action

import hu.naturlecso.pdpd.data.BuildConfig
import hu.naturlecso.pdpd.data.api.ContactDetailsApiModel
import hu.naturlecso.pdpd.data.api.PersonApiModel
import hu.naturlecso.pdpd.data.api.PipedrivePersonsApiService
import hu.naturlecso.pdpd.data.cache.ContactDetailsDataModel
import hu.naturlecso.pdpd.data.cache.ContactDetailsTypeDataModel
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
        ).apply { contactDetails = mapPhones(apiModel) + mapEmails(apiModel) }

    private fun mapContactDetailsApiModelToDataModel(
        apiModel: ContactDetailsApiModel,
        personId: Int,
        type: ContactDetailsTypeDataModel) =
        ContactDetailsDataModel(
            id = UUID.randomUUID().toString(),
            type = type,
            label = apiModel.label,
            value = apiModel.value,
            primary = apiModel.primary,
            personId = personId
        )

    private fun mapPhones(apiModel: PersonApiModel): List<ContactDetailsDataModel> {
        return apiModel.phone
            .filter { contactDetails -> contactDetails.value.isNotEmpty() }
            .map { contactDetails -> mapContactDetailsApiModelToDataModel(
                contactDetails, apiModel.id, ContactDetailsTypeDataModel.PHONE) }
    }

    private fun mapEmails(apiModel: PersonApiModel): List<ContactDetailsDataModel> {
        return apiModel.email
            .filter { contactDetails -> contactDetails.value.isNotEmpty() }
            .map { contactDetails -> mapContactDetailsApiModelToDataModel(
                contactDetails, apiModel.id, ContactDetailsTypeDataModel.EMAIL) }
    }
}
