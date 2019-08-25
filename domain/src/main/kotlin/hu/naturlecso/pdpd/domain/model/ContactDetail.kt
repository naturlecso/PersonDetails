package hu.naturlecso.pdpd.domain.model

data class ContactDetail(
    val type: ContactDetailType,
    val label: String,
    val value: String,
    val primary: Boolean
)
