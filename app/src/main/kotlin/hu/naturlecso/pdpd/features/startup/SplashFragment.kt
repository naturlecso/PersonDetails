package hu.naturlecso.pdpd.features.startup

import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.navigation.NavigationCommand
import hu.naturlecso.pdpd.common.navigation.Navigator
import hu.naturlecso.pdpd.common.util.disposeIfNeeded
import hu.naturlecso.pdpd.common.view.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel>() {
    override val layoutRes: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    private val navigator: Navigator by inject()

    private lateinit var disposable: Disposable

    override fun afterViews() {
        viewModel.refreshPersonsAndNavigateCommand.execute()

        disposable = viewModel.errorEvent.firstElement()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showErrorSnackBar(it) }
    }

    override fun onDestroy() {
        disposable.disposeIfNeeded()

        super.onDestroy()
    }

    private fun showErrorSnackBar(errorState: SplashErrorState) {
        val continueClickListener = View.OnClickListener {
            navigator.navigate(NavigationCommand.To(SplashFragmentDirections.toPersonList()))
        }

        when(errorState) {
            SplashErrorState.REFRESH_ERROR ->
                createSnackBar(R.string.splash_error_refresh, continueClickListener)
            SplashErrorState.OFFLINE ->
                createSnackBar(R.string.splash_error_offline, continueClickListener)
            SplashErrorState.OFFLINE_NO_DATA ->
                createSnackBar(R.string.splash_error_offline, null)
        }.run { show() }
    }

    private fun createSnackBar(@StringRes textRes: Int, action: View.OnClickListener?) =
        Snackbar.make(parent, textRes, Snackbar.LENGTH_INDEFINITE)
            .apply { action?.let { setAction(R.string.splash_error_action, action) } }
            .apply { view.setBackgroundColor(ContextCompat.getColor(context, R.color.error)) }
            .apply { setActionTextColor(ContextCompat.getColor(context, R.color.background)) }
}
