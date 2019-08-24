package hu.naturlecso.pdpd

import hu.naturlecso.pdpd.common.navigation.DefaultNavigator
import hu.naturlecso.pdpd.common.navigation.Navigator
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { DefaultNavigator() }
}
