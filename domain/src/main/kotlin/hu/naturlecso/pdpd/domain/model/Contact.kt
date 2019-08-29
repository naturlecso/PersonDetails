package hu.naturlecso.pdpd.domain.model

data class Contact(
    val type: ContactType,
    val label: String?,
    val value: String,
    val primary: Boolean
)
