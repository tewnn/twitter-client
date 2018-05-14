package com.tewnn.twitterclient.network.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface StatusesEndpoint {
    @GET("statuses/sample.json")
    @Streaming
    fun sampleStream(): Single<ResponseBody>

//    @GET("statuses/home_timeline.json")
//    @Streaming
//    fun timeline(): Call<ResponseBody>
}