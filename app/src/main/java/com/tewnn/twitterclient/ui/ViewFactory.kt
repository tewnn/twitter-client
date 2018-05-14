package com.tewnn.twitterclient.ui

import android.content.Context
import com.tewnn.twitterclient.ui.tweets.TweetsListPresenter
import com.tewnn.twitterclient.ui.tweets.TweetsListView

class ViewFactory(
        private val context: Context
) : IViewFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <View : IUIView> createView(presenter: UIPresenter<View>): View = when (presenter) {
        is TweetsListPresenter -> TweetsListView(context) as View
        else -> throw IllegalArgumentException("Unknown presenter type: $presenter")
    }
}