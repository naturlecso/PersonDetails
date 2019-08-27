package hu.naturlecso.pdpd.features.persons.list

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import hu.naturlecso.pdpd.common.binding.command
import hu.naturlecso.pdpd.common.navigation.Navigator
import hu.naturlecso.pdpd.domain.model.Person
import hu.naturlecso.pdpd.domain.store.PersonStore
import timber.log.Timber

class PersonsViewModel(
    private val navigator: Navigator,
    personStore: PersonStore
) : ViewModel() {

    val persons = LiveDataReactiveStreams.fromPublisher(personStore.getList())

    val selectPersonCommand = command<Person> {
            person -> Timber.d(person.toString()) }
}
