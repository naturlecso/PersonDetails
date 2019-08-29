package hu.naturlecso.pdpd.data.cache

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class PersonDataModel(
    @PrimaryKey val id: Int,
    val name: String,
    val ownerName: String,
    val ownerEmail: String,
    val organizationName: String?,
    val organizationAddress: String?,
    val openDealsCount: Int,
    val closedDealsCount: Int,
    val wonDealsCount: Int,
    val lostDealsCount: Int
) {
    @Ignore
    var contacts: List<ContactDataModel> = emptyList()
}

@Entity
data class ContactDataModel(
    @PrimaryKey val id: String,
    val type: ContactTypeDataModel,
    val label: String?,
    val value: String,
    val primary: Boolean,
    val personId: Int
)

enum class ContactTypeDataModel {
    PHONE, EMAIL
}
