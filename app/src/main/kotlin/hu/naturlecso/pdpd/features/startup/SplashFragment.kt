package hu.naturlecso.pdpd.features.startup

import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.view.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel>() {
    override val layoutRes: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun afterViews() {
        viewModel.refreshPersonsAndNavigateCommand.execute()
    }
}
