package hu.naturlecso.pdpd.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable

@Dao
abstract class PersonDao {
    @Query("SELECT * FROM PersonDataModel")
    abstract fun getAll(): Flowable<List<PersonDataModel>>

    @Query("SELECT * FROM PersonDataModel WHERE id = :id")
    abstract fun get(id: Int): Flowable<PersonDataModel>

    @Query("SELECT * FROM ContactDataModel WHERE personId = :personId")
    abstract fun getContactsByPerson(personId: Int): Flowable<List<ContactDataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPerson(person: PersonDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContacts(contacts: List<ContactDataModel>)

    @Query("DELETE FROM PersonDataModel")
    abstract fun deletePersons()

    @Query("DELETE FROM ContactDataModel")
    abstract fun deleteContacts()

    @Transaction
    open fun deleteAll() {
        deletePersons()
        deleteContacts()
    }

    @Transaction
    open fun insertAll(persons: List<PersonDataModel>) {
        persons.forEach { person ->
            run {
                insertPerson(person)
                insertContacts(person.contacts ?: emptyList())
            }
        }
    }

    @Transaction
    open fun replaceAll(persons: List<PersonDataModel>) {
        deleteAll()
        insertAll(persons)
    }
}
