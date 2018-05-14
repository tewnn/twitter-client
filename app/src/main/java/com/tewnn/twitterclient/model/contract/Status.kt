package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName
import com.tewnn.twitterclient.model.TweetId
import com.tewnn.twitterclient.model.UserId

data class Status(
        @SerializedName("id") val id: TweetId,
        @SerializedName("user_id") val userId: UserId?
)