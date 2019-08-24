package hu.naturlecso.pdpd.common.navigation

import io.reactivex.Flowable

interface Navigator {
    fun navigate(navigationCommand: NavigationCommand)
    fun navigationEvents(): Flowable<NavigationCommand>
}
