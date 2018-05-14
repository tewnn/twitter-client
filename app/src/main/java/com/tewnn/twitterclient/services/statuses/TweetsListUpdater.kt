package com.tewnn.twitterclient.services.statuses

import com.tewnn.twitterclient.model.TweetAdded
import com.tewnn.twitterclient.model.TweetDeleted
import com.tewnn.twitterclient.model.TweetUpdate
import com.tewnn.twitterclient.model.contract.Tweet

class TweetsListUpdater(
        val tweetsListMaxSize: Int
) {
    fun applyUpdates(tweets: List<Tweet>, updates: List<TweetUpdate>): List<Tweet> {
        val mutableTweets = tweets.toMutableList()

        updates.forEach { update ->
            when (update) {
                is TweetAdded -> {
                    if (mutableTweets.size < tweetsListMaxSize) {
                        mutableTweets.add(update.tweet)
                    }
                }
                is TweetDeleted -> mutableTweets.removeAll { tweet -> tweet.id == update.id }
            }
        }

        return mutableTweets
    }
}