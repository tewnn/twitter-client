package com.tewnn.twitterclient.model.contract

import org.joda.time.DateTime

data class StreamError(
        val message: String?,
        val sent: DateTime?
)