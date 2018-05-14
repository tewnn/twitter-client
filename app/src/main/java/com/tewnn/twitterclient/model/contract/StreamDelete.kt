package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName

data class StreamDelete(
        @SerializedName("status") val status: Status
)