package hu.naturlecso.pdpd.features.persons.details

import androidx.databinding.ObservableField
import hu.naturlecso.pdpd.common.model.RowViewModel
import hu.naturlecso.pdpd.domain.model.Contact

class ContactListItemViewModel(contact: Contact) : RowViewModel<Contact>(contact) {
    val contact = ObservableField(contact)
}
