package com.tewnn.twitterclient.model.contract

import com.google.gson.annotations.SerializedName
import com.tewnn.twitterclient.model.UserId

data class User(
        @SerializedName("id") val id: UserId,
        @SerializedName("name") val name: String,
        @SerializedName("screen_name") val screenName: String?,
        @SerializedName("profile_image_url") val profileImageUrl: String?
)