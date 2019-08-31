package hu.naturlecso.pdpd

import android.content.Context
import android.net.ConnectivityManager
import hu.naturlecso.pdpd.common.navigation.DefaultNavigator
import hu.naturlecso.pdpd.common.navigation.Navigator
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { DefaultNavigator() }

    single {
        get<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
