package com.tewnn.twitterclient.services

import com.tewnn.twitterclient.model.exceptions.NetworkException
import io.reactivex.Observable
import retrofit2.HttpException
import java.io.IOException

fun <T> Observable<T>.replaceNetworkExceptions(): Observable<T> = this.onErrorResumeNext { throwable: Throwable ->
    val replaced = when (throwable) {
        is IOException, is HttpException -> NetworkException(throwable.message, throwable)
        else -> throwable
    }

    Observable.error<T>(replaced)
}