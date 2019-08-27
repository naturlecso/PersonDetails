package hu.naturlecso.pdpd.features.persons.list

import androidx.databinding.ObservableField
import hu.naturlecso.pdpd.common.model.RowViewModel
import hu.naturlecso.pdpd.domain.model.Person

class PersonListItemViewModel(person: Person) : RowViewModel<Person>(person) {
    val person = ObservableField(person)
}
