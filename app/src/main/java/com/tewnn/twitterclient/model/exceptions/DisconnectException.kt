package com.tewnn.twitterclient.model.exceptions

import com.tewnn.twitterclient.model.contract.StreamDisconnect

class DisconnectException(
        val disconnect: StreamDisconnect?,
        cause: Throwable? = null
) : Exception("Disconnected: ${disconnect ?: cause?.message}", cause)