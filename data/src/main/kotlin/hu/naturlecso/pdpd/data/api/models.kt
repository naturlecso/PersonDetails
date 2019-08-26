package hu.naturlecso.pdpd.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonsResponse(
    val success: Boolean,
    val data: List<PersonApiModel>
)

@Serializable
data class PersonApiModel(
    val id: Int,
    val name: String,
    @SerialName("owner_id") val owner: OwnerApiModel,
    @SerialName("org_id") val organization: OrganizationApiModel?,
    @SerialName("open_deals_count") val openDealsCount: Int,
    @SerialName("closed_deals_count") val closedDealsCount: Int,
    @SerialName("won_deals_count") val wonDealsCount: Int,
    @SerialName("lost_deals_count") val lostDealsCount: Int,
    val phone: List<ContactDetailsApiModel>,
    val email: List<ContactDetailsApiModel>
)

@Serializable
data class OwnerApiModel(
    val name: String,
    val email: String
)

@Serializable
data class OrganizationApiModel(
    val name: String,
    val address: String?
)

@Serializable
data class ContactDetailsApiModel(
    val label: String? = null,
    val value: String,
    val primary: Boolean
)
