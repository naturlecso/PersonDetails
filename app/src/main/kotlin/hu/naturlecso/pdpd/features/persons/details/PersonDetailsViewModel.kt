package hu.naturlecso.pdpd.features.persons.details

import androidx.lifecycle.ViewModel
import com.jakewharton.rx.ReplayingShare
import hu.naturlecso.pdpd.common.util.asLiveData
import hu.naturlecso.pdpd.domain.model.ContactType
import hu.naturlecso.pdpd.domain.store.PersonStore
import io.reactivex.processors.BehaviorProcessor

class PersonDetailsViewModel(
    personStore: PersonStore
) : ViewModel() {
    private val personIdProcessor = BehaviorProcessor.create<Int>()

    fun setPersonId(personId: Int) {
        personIdProcessor.onNext(personId)
    }

    private val personFlowable = personIdProcessor
        .flatMapSingle { personStore.get(it).firstOrError() }
        .compose(ReplayingShare.instance())

    val person = personFlowable.asLiveData()

    val email = personFlowable
        .map {person ->
            val hasEmail = person.contact
                .map { it.type }
                .any { it == ContactType.EMAIL }

            return@map if (hasEmail) {
                person.contact
                    .filter { it.type == ContactType.EMAIL }
                    .first { it.primary }
                    .value
            } else {
                ""
            }
        }
        .asLiveData()

    val contacts = personFlowable
        .map { person -> person.contact
            .filter { it.primary }
            .sortedBy { it.type }
        }
        .asLiveData()

    val showContacts = personFlowable
        .map { it.contact.isNotEmpty() }
        .asLiveData()
}
