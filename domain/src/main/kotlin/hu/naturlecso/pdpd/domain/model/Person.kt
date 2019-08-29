package hu.naturlecso.pdpd.domain.model

data class Person(
    val id: Int,
    val name: String,
    val owner: Owner,
    val organization: Organization?,
    val contact: List<Contact>,
    val openDealsCount: Int,
    val closedDealsCount: Int,
    val wonDealsCount: Int,
    val lostDealsCount: Int
)
