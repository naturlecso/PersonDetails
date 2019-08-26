package hu.naturlecso.pdpd.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PipedrivePersonsApiService {

    @GET("persons")
    fun allPersons(@Query("api_token") apiToken: String): Single<PersonsResponse>
}
