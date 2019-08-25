package hu.naturlecso.pdpd.domain.action

import io.reactivex.Completable

interface PersonAction {
    fun refresh(): Completable
}
