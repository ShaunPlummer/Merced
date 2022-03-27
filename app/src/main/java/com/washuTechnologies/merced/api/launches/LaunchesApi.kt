package com.washuTechnologies.merced.api.launches

import com.squareup.moshi.Moshi
import com.washuTechnologies.merced.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.Executors

/**
 * API to retrieve information about upcoming and past RocketLaunches
 */
interface LaunchesApi {

    /**
     * Retrieve the complete list of rocket launches.
     */
    @GET("launches")
    suspend fun getRocketLaunchList(): Array<RocketLaunch>

    /**
     * Retrieve information about a single rocket launch using it's id.
     */
    @GET("launches/{id}")
    suspend fun getRocketLaunch(@Path("id") id: String): RocketLaunch


    companion object {
        private val loggingInterceptor: HttpLoggingInterceptor
            get() {
                val interceptor = HttpLoggingInterceptor()
                return interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            }

        /**
         * Factory method to create a Retrofit API.
         */
        fun create(): LaunchesApi {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                client.addInterceptor(loggingInterceptor)
            }

            return Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v4/")
                .client(client.build())
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
