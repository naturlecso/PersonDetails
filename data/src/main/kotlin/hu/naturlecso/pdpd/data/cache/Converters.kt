package hu.naturlecso.pdpd.data.cache

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun contactTypeDataModelToDb(value: ContactTypeDataModel?) = value?.name

    @TypeConverter
    fun contactTypeDataModelFromDb(value: String) = ContactTypeDataModel.valueOf(value)
}