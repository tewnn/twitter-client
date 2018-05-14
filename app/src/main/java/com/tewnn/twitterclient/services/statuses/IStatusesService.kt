package com.tewnn.twitterclient.services.statuses

import com.tewnn.twitterclient.model.contract.Tweet
import io.reactivex.Observable

interface IStatusesService {
    val sampleStatusesStream: Observable<List<Tweet>>
}