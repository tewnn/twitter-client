package com.tewnn.twitterclient

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import net.danlew.android.joda.JodaTimeAndroid
import java.io.IOException
import java.net.SocketException

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        RxJavaPlugins.setErrorHandler { t ->
            Log.e(Constants.Log.tagRx, "Unhandled exception: $t", t)
            t.printStackTrace()
            when (t) {
                is UndeliverableException -> when (t.cause) {
                    is IOException, is SocketException -> Unit
                    else -> throw t
                }
                else -> throw t
            }
        }
    }
}