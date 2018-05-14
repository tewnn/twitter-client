package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName
import com.tewnn.twitterclient.model.TweetId
import org.joda.time.DateTime

data class Tweet(
        @SerializedName("id") val id: TweetId,
        @SerializedName("id_str") val idStr: String?,
        @SerializedName("text") val text: String?,
        @SerializedName("user") val user: User?,
        @SerializedName("created_at") val createdAt: DateTime?,
        @SerializedName("retweet_count") val retweetCount: Int?,
        @SerializedName("favorite_count") val favoriteCount: Int?
) {
    val isComplete: Boolean get() = idStr != null
}