package com.tewnn.twitterclient.adapters.tweets

import android.net.Uri
import android.view.View
import com.tewnn.twitterclient.R
import com.tewnn.twitterclient.adapters.BaseViewHolder
import com.tewnn.twitterclient.model.contract.Tweet
import kotlinx.android.synthetic.main.item_tweet.*

class TweetViewHolder(itemView: View) : BaseViewHolder(itemView) {
    fun bind(tweet: Tweet) {
        textTweetText.text = tweet.text
        textName.text = tweet.user?.name
        textScreenName.text = "@${tweet.user?.screenName}"
        imageUserpic.loadPicture(tweet.user?.profileImageUrl?.let { url -> Uri.parse(url) })
        textTweetStatistics.text =
                listOf(
                        (tweet.retweetCount ?: 0).let { retweets -> context.resources.getQuantityString(R.plurals.tweet_n_retweets, retweets, retweets) },
                        (tweet.favoriteCount ?: 0).let { favorites -> context.resources.getQuantityString(R.plurals.tweet_n_favorites, favorites, favorites) }
                )
                        .joinToString(" · ")
    }
}