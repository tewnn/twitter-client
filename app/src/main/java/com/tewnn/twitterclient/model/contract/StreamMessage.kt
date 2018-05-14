package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName

data class StreamMessage(
        @SerializedName("error") val error: StreamError?,
        @SerializedName("disconnect") val disconnect: StreamDisconnect?,
        @SerializedName("delete") val delete: StreamDelete?
)