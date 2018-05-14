package com.tewnn.twitterclient.model.exceptions

import com.tewnn.twitterclient.model.contract.StreamError

class ServerException(
        val streamError: StreamError
) : Exception(streamError.message)