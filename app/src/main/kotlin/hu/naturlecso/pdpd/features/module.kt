package hu.naturlecso.pdpd.features

import hu.naturlecso.pdpd.features.persons.details.PersonDetailsViewModel
import hu.naturlecso.pdpd.features.persons.list.PersonsViewModel
import hu.naturlecso.pdpd.features.startup.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { PersonsViewModel(get(), get()) }
    viewModel { PersonDetailsViewModel(get()) }
}
