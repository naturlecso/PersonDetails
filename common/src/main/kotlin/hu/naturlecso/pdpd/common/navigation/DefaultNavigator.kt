package hu.naturlecso.pdpd.common.navigation

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class DefaultNavigator: Navigator {

    private val navigationProcessor = PublishProcessor.create<NavigationCommand>()

    override fun navigationEvents(): Flowable<NavigationCommand> = navigationProcessor

    override fun navigate(navigationCommand: NavigationCommand) {
        navigationProcessor.onNext(navigationCommand)
    }
}
