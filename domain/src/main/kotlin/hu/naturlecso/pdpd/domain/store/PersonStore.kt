package hu.naturlecso.pdpd.domain.store

import hu.naturlecso.pdpd.domain.model.Person
import io.reactivex.Flowable
import io.reactivex.Single

interface PersonStore {
    fun getList(): Flowable<List<Person>>
    fun get(id: Int): Single<Person>
}
