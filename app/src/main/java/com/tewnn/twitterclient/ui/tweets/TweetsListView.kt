package com.tewnn.twitterclient.ui.tweets

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import com.tewnn.twitterclient.R
import com.tewnn.twitterclient.adapters.tweets.TweetsListAdapter
import com.tewnn.twitterclient.model.contract.Tweet
import com.tewnn.twitterclient.ui.UIView
import com.tewnn.twitterclient.ui.util.refreshes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.ui_tweets_list.view.*


class TweetsListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : UIView(context, attrs, defStyleAttr), ITweetsListView {

    private val layoutManager by lazy { LinearLayoutManager(context) }
    private val adapter by lazy { TweetsListAdapter(context) }
    private val resubscribesSubject = PublishSubject.create<Unit>()

    override val refreshes: Observable<Unit> get() = swipeForRefresh.refreshes()
    override val resubscribes: Observable<Unit> get() = resubscribesSubject.hide()

    init {
        inflate(R.layout.ui_tweets_list)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun setTweets(tweets: List<Tweet>) {
        swipeForRefresh.isRefreshing = false
        adapter.items = tweets
    }

    override fun showStreamErrorMessage(error: ITweetsListView.StreamError) {
        swipeForRefresh.isRefreshing = false

        showResubscribeSnackbar(when (error) {
            ITweetsListView.StreamError.Unknown -> R.string.lbl_tweet_list_error_unknown
            ITweetsListView.StreamError.NoInternetConnection -> R.string.lbl_tweet_list_error_no_internet_connection
            ITweetsListView.StreamError.StreamDisconnected -> R.string.lbl_tweet_list_error_stream_disconnected
            ITweetsListView.StreamError.ServerError -> R.string.lbl_tweet_list_error_server
            ITweetsListView.StreamError.ProtocolProblem -> R.string.lbl_tweet_list_error_protocol_problem
        })
    }

    private fun showResubscribeSnackbar(@StringRes textResId: Int) {
        val snackbar = Snackbar
                .make(coordinatorLayout, textResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.btn_tweet_list_resubscribe, { resubscribesSubject.onNext(Unit) })

        snackbar.show()
    }
}