package hu.naturlecso.pdpd.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.navigation.NavigationCommand
import hu.naturlecso.pdpd.common.navigation.Navigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val navigator: Navigator by inject()

    private lateinit var navController: NavController

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)

        disposable = navigator.navigationEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is NavigationCommand.To -> navController.navigate(it.directions)
                    is NavigationCommand.BackTo -> {
                        navController.popBackStack(it.destinationId, false)
                        navController.navigate(it.destinationId)
                    }
                    is NavigationCommand.Back -> navController.navigateUp()
                    is NavigationCommand.ToRoot -> navController.popBackStack(
                        R.id.personsFragment, false
                    )
                }
            }
    }

    override fun onDestroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }

        super.onDestroy()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}
