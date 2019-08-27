package hu.naturlecso.pdpd.features

import hu.naturlecso.pdpd.features.startup.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { SplashViewModel(get(), get()) }
}
