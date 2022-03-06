package com.washuTechnologies.merced.api.launches


import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.Executors

/**
 * API to retrieve information about upcoming and past RocketLaunches
 */
interface LaunchesApi {

    /**
     * Retrieve the complete list of rocket launches.
     */
    @GET("launches")
    suspend fun getRocketLaunchList(): List<RocketLaunch>

    companion object {

        /**
         * Factory method to create a Retrofit API.
         */
        fun create(): LaunchesApi {
            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v4/")
                .client(client)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .build()
                    )
                )
                .build()
                .create(LaunchesApi::class.java)
        }
    }
}
