package hu.naturlecso.pdpd.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [PersonDataModel::class, ContactDetailsDataModel::class]
)
@TypeConverters(Converters::class)
abstract class PipedrivePersonsDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
