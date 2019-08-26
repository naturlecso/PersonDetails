package hu.naturlecso.pdpd.domain.store

import hu.naturlecso.pdpd.domain.model.Person
import io.reactivex.Flowable

interface PersonStore {
    fun getList(): Flowable<List<Person>>
    fun get(id: Int): Flowable<Person>
}
