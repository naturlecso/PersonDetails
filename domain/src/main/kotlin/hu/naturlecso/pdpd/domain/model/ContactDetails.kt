package hu.naturlecso.pdpd.domain.model

data class ContactDetails(
    val type: ContactDetailsType,
    val label: String?,
    val value: String,
    val primary: Boolean
)
