package hu.naturlecso.pdpd.features.startup

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import hu.naturlecso.pdpd.common.binding.command
import hu.naturlecso.pdpd.common.navigation.NavigationCommand.To
import hu.naturlecso.pdpd.common.navigation.Navigator
import hu.naturlecso.pdpd.common.util.isNetworkAvailable
import hu.naturlecso.pdpd.domain.action.PersonAction
import hu.naturlecso.pdpd.domain.store.PersonStore
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.processors.PublishProcessor

class SplashViewModel(
    navigator: Navigator,
    personAction: PersonAction,
    personStore: PersonStore,
    connectivityManager: ConnectivityManager
) : ViewModel() {

    val errorEvent = PublishProcessor.create<SplashErrorState>()

    private val deviceOnlineCompletable = Single.zip<Boolean, Boolean, Boolean>(
        Single.fromCallable { isNetworkAvailable(connectivityManager) },
        personStore.getList()
            .first(emptyList())
            .map { it.isEmpty() },
        BiFunction { isInternetAvailable, isStoreEmpty ->
            if (!isInternetAvailable) {
                if (isStoreEmpty) {
                    errorEvent.onNext(SplashErrorState.OFFLINE_NO_DATA)
                } else {
                    errorEvent.onNext(SplashErrorState.OFFLINE)
                }

                false
            } else {
                true
            }
        } )

    val refreshPersonsAndNavigateCommand = command {
        deviceOnlineCompletable
            .filter { it }
            .flatMapCompletable {
                personAction.refresh()
                    .doOnError { errorEvent.onNext(SplashErrorState.REFRESH_ERROR) }
                    .andThen(Completable.fromAction {
                        navigator.navigate(To(SplashFragmentDirections.toPersonList()))
                    })
            }
            .onErrorComplete()
            .subscribe()
    }
}
