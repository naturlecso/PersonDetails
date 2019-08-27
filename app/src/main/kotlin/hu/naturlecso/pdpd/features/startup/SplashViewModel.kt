package hu.naturlecso.pdpd.features.startup

import androidx.lifecycle.ViewModel
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.binding.command
import hu.naturlecso.pdpd.common.navigation.NavigationCommand.To
import hu.naturlecso.pdpd.common.navigation.NavigationCommand.BackTo
import hu.naturlecso.pdpd.common.navigation.Navigator
import hu.naturlecso.pdpd.domain.action.PersonAction
import io.reactivex.Completable

class SplashViewModel(
    private val navigator: Navigator,
    private val personAction: PersonAction
) : ViewModel() {

    val refreshPersonsAndNavigateCommand = command {
        personAction.refresh()
            .andThen(Completable.fromAction {
                navigator.navigate(To(SplashFragmentDirections.fromSplash()))
            } )
            .subscribe()
    }
}
