package com.tewnn.twitterclient.logger

interface ILogger {
    fun d(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}