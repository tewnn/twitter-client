package com.tewnn.twitterclient.ui.util

import android.os.Looper

object TreadUtil {
    fun checkThread() {
        if (Looper.getMainLooper().thread != Thread.currentThread()) {
            throw IllegalAccessException("Cannot use cache from non-main thread: ${Thread.currentThread().id}")
        }
    }
}