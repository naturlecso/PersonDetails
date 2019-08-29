package hu.naturlecso.pdpd.features.persons.details

import androidx.databinding.ObservableField
import hu.naturlecso.pdpd.common.model.RowViewModel
import hu.naturlecso.pdpd.domain.model.ContactDetails

class ContactListItemViewModel(contact: ContactDetails) : RowViewModel<ContactDetails>(contact) {
    val contact = ObservableField(contact)
}
