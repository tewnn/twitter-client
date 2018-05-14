package com.tewnn.twitterclient.ui.tweets

import com.tewnn.twitterclient.Constants
import com.tewnn.twitterclient.model.exceptions.DeserializationException
import com.tewnn.twitterclient.model.exceptions.DisconnectException
import com.tewnn.twitterclient.model.exceptions.NetworkException
import com.tewnn.twitterclient.model.exceptions.ServerException
import com.tewnn.twitterclient.ui.UIPresenter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class TweetsListPresenter : UIPresenter<ITweetsListView>() {

    private var tweetsSubscription: Disposable? = null

    override fun subscribe() {
        super.subscribe()

        resubscribeOnTweets()

        Observable.merge(view.refreshes, view.resubscribes)
                .subscribe { resubscribeOnTweets() }
                .register()
    }

    override fun unsubscribe() {
        super.unsubscribe()
        tweetsSubscription?.dispose()
    }

    private fun resubscribeOnTweets() {
        tweetsSubscription?.dispose()

        tweetsSubscription = services.statusesService.sampleStatusesStream
                .observeOn(scheduler)
                .subscribe(
                        view::setTweets,
                        { throwable ->
                            logger.d(Constants.Log.tagUi, "[TweetsListPresenter] Tweets stream error: $throwable", throwable)
                            val error = when (throwable) {
                                is NetworkException -> ITweetsListView.StreamError.NoInternetConnection
                                is DisconnectException -> ITweetsListView.StreamError.StreamDisconnected
                                is ServerException -> ITweetsListView.StreamError.ServerError
                                is DeserializationException -> ITweetsListView.StreamError.ProtocolProblem
                                else -> ITweetsListView.StreamError.Unknown
                            }
                            view.showStreamErrorMessage(error)
                        }
                )
    }

}