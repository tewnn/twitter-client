package com.tewnn.twitterclient.services.statuses

import com.tewnn.twitterclient.model.TweetUpdate
import com.tewnn.twitterclient.model.contract.Tweet
import com.tewnn.twitterclient.model.exceptions.DisconnectException
import com.tewnn.twitterclient.network.api.StatusesEndpoint
import com.tewnn.twitterclient.serialization.StreamDeserializer
import com.tewnn.twitterclient.services.replaceNetworkExceptions
import io.reactivex.Observable
import java.io.EOFException
import java.util.concurrent.TimeUnit

class StatusesService(
        private val statusesEndpoint: StatusesEndpoint,
        private val streamDeserializer: StreamDeserializer,
        tweetsListMaxSize: Int = 10000
) : IStatusesService {

    private val tweetsListUpdater = TweetsListUpdater(tweetsListMaxSize)

    val sampleStatusesUpdatesStream: Observable<TweetUpdate> by lazy {
        statusesEndpoint.sampleStream()
                .flatMapObservable { responseBody ->
                    val source = responseBody.source()
                    Observable.create<TweetUpdate> { emitter ->
                        try {
                            while (!emitter.isDisposed && !source.exhausted()) {
                                val str = source.readUtf8Line()
                                if (str != null && str.isNotBlank()) {
                                    val tweetUpdate = streamDeserializer.deserialize(str)
                                    if (tweetUpdate != null) {
                                        emitter.onNext(tweetUpdate)
                                    }
                                }
                            }
                        } catch (ex: EOFException) {
                            throw DisconnectException(null, ex)
                        }
                    }
                }
                .replaceNetworkExceptions()
    }

    override val sampleStatusesStream: Observable<List<Tweet>> by lazy {
        sampleStatusesUpdatesStream
                .buffer(1L, TimeUnit.SECONDS)
                .filter { tweets -> tweets.isNotEmpty() }
                .scan(emptyList(), tweetsListUpdater::applyUpdates)
    }
}