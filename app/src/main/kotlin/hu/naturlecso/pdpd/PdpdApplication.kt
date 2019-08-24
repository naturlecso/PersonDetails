package hu.naturlecso.pdpd

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import hu.naturlecso.pdpd.data.dataModule
import hu.naturlecso.pdpd.features.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PdpdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if  (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@PdpdApplication)

            modules(appModule)
            modules(dataModule)
            modules(featureModule)
        }
    }
}
