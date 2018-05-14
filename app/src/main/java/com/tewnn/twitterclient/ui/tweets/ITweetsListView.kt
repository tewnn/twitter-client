package com.tewnn.twitterclient.ui.tweets

import com.tewnn.twitterclient.model.contract.Tweet
import com.tewnn.twitterclient.ui.IUIView
import io.reactivex.Observable

interface ITweetsListView : IUIView {
    enum class StreamError {
        Unknown,
        NoInternetConnection,
        StreamDisconnected,
        ServerError,
        ProtocolProblem,
    }

    val refreshes: Observable<Unit>
    val resubscribes: Observable<Unit>

    fun setTweets(tweets: List<Tweet>)
    fun showStreamErrorMessage(error: StreamError)
}