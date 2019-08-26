package hu.naturlecso.pdpd.data

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.naturlecso.pdpd.data.action.DefaultPersonAction
import hu.naturlecso.pdpd.data.api.PipedrivePersonsApiService
import hu.naturlecso.pdpd.data.cache.PipedrivePersonsDatabase
import hu.naturlecso.pdpd.data.store.DefaultPersonStore
import hu.naturlecso.pdpd.domain.action.PersonAction
import hu.naturlecso.pdpd.domain.store.PersonStore
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

val dataModule = module {
    val baseUrl = "https://api.pipedrive.com/v1/"
    val contentType = MediaType.get("application/json")
    val cacheSize: Long = 10 * 1024 * 1024

    val databaseName = "pdpd.db"

    // api
    single { Cache(get<Application>().cacheDir, cacheSize) }

    single<Interceptor> { HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY } }

    single { OkHttpClient.Builder()
        .cache(get())
        .addInterceptor(get())
        .build() }

    single { Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(get())
        .build() }

    single { get<Retrofit>().create(PipedrivePersonsApiService::class.java) }

    // cache
    single { Room.databaseBuilder(get(), PipedrivePersonsDatabase::class.java, databaseName).build() }
    single { get<PipedrivePersonsDatabase>().personDao() }

    // action
    single<PersonAction> { DefaultPersonAction(get(), get()) }

    // store
    single<PersonStore> { DefaultPersonStore(get()) }
}
