package com.tewnn.twitterclient

import com.tewnn.twitterclient.logger.ILogger

object TestLogger : ILogger {
    override fun d(tag: String, message: String, throwable: Throwable?) = print("D", tag, message, throwable)

    override fun e(tag: String, message: String, throwable: Throwable?) = print("E", tag, message, throwable)

    private fun print(level: String, tag: String, message: String, throwable: Throwable?) {
        System.out.println("$level/$tag: $message ${throwable?.let { "\nthrowable: $throwable" } ?: ""}")
    }
}