package com.tewnn.twitterclient.config

data class EndpointsConfig(
        val streamingHost: String,
        val streamingSchema: String = "https"
) {
    val streamingServerUrl = "$streamingSchema://$streamingHost"
    val streamingBaseUrl: String = "$streamingServerUrl/1.1/"
}