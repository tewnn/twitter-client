package com.tewnn.twitterclient.model

import com.tewnn.twitterclient.model.contract.Tweet

sealed class TweetUpdate

data class TweetAdded(
        val tweet: Tweet
) : TweetUpdate()

data class TweetDeleted(
        val id: TweetId
) : TweetUpdate()