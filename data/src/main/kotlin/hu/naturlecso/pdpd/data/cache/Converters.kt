package hu.naturlecso.pdpd.data.cache

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun contactDetailsTypeDataModelToDb(value: ContactDetailsTypeDataModel?) = value?.name

    @TypeConverter
    fun contactDetailsTypeDataModelFromDb(value: String) = ContactDetailsTypeDataModel.valueOf(value)
}