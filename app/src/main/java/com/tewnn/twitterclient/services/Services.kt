package com.tewnn.twitterclient.services

import com.tewnn.twitterclient.logger.ILogger
import com.tewnn.twitterclient.network.api.Endpoints
import com.tewnn.twitterclient.serialization.GsonFactory
import com.tewnn.twitterclient.serialization.StreamDeserializer
import com.tewnn.twitterclient.services.statuses.StatusesService

class Services(
        private val endpoints: Endpoints,
        private val gsonFactory: GsonFactory,
        private val logger: ILogger
) : IServices {
    override val statusesService: StatusesService by lazy {
        StatusesService(endpoints.statusesEndpoint, StreamDeserializer(gsonFactory.createGson(), logger))
    }
}