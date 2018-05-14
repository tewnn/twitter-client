package com.tewnn.twitterclient.network.api

import com.tewnn.twitterclient.AppEnvironment
import com.tewnn.twitterclient.config.AuthConfig
import com.tewnn.twitterclient.config.AuthToken
import com.tewnn.twitterclient.endpointsConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit

class Endpoints(
        private val authConfig: AuthConfig,
        private val authToken: AuthToken
) {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(SigningInterceptor(createOAuthConsumer()))
                .build()
    }

    private val rxAdapter by lazy { RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()) }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(AppEnvironment.current.endpointsConfig.streamingBaseUrl)
                .addCallAdapterFactory(rxAdapter)
                .client(okHttpClient)
                .build()
    }

    val statusesEndpoint: StatusesEndpoint by lazy { retrofit.create(StatusesEndpoint::class.java) }

    private fun createOAuthConsumer(): OkHttpOAuthConsumer =
            OkHttpOAuthConsumer(authConfig.apiKey, authConfig.apiSecret)
                    .apply {
                        setTokenWithSecret(authToken.accessToken, authToken.accessTokenSecret)
                    }
}