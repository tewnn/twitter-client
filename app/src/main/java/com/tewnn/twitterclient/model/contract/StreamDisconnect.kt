package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName

data class StreamDisconnect(
        @SerializedName("code") val code: Int,
        @SerializedName("stream_name") val streamName: String?,
        @SerializedName("reason") val reason: String?
)