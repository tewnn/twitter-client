package com.tewnn.twitterclient

import android.os.Bundle
import com.tewnn.twitterclient.ui.UIActivity
import com.tewnn.twitterclient.ui.tweets.TweetsListPresenter

class MainActivity : UIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushPresenter(TweetsListPresenter())
    }
}
