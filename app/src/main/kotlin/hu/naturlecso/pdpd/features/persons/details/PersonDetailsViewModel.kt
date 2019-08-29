package hu.naturlecso.pdpd.features.persons.details

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.jakewharton.rx.ReplayingShare
import hu.naturlecso.pdpd.domain.model.ContactDetailsType
import hu.naturlecso.pdpd.domain.store.PersonStore
import io.reactivex.processors.BehaviorProcessor

class PersonDetailsViewModel(
    personStore: PersonStore
) : ViewModel() {
    private val personIdProcessor = BehaviorProcessor.create<Int>()

    fun setPersonId(personId: Int) {
        personIdProcessor.onNext(personId)
    }

    val personFlowable = personIdProcessor
        .flatMapSingle { personStore.get(it).firstOrError() }
        .compose(ReplayingShare.instance())

    val person = LiveDataReactiveStreams.fromPublisher(personFlowable)

    val email = LiveDataReactiveStreams.fromPublisher(
        personFlowable
            .map {person ->
                val hasEmail = person.contactDetails
                    .map { it.type }
                    .any { it == ContactDetailsType.EMAIL }

                return@map if (hasEmail) {
                    person.contactDetails
                        .filter { it.type == ContactDetailsType.EMAIL }
                        .first { it.primary }
                        .value
                } else {
                    ""
                }
            }
    )

    val contacts = LiveDataReactiveStreams.fromPublisher(
        personFlowable.map { person ->
            person.contactDetails
                .filter { it.primary }
                .sortedBy { it.type }
        }
    )

    val showContacts = LiveDataReactiveStreams.fromPublisher(
        personFlowable.map { it.contactDetails.isNotEmpty() }
    )
}
