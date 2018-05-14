package com.tewnn.twitterclient

import com.tewnn.twitterclient.logger.AndroidLogger
import com.tewnn.twitterclient.logger.ILogger
import com.tewnn.twitterclient.network.api.Endpoints
import com.tewnn.twitterclient.serialization.GsonFactory
import com.tewnn.twitterclient.services.IServices
import com.tewnn.twitterclient.services.Services
import com.tewnn.twitterclient.ui.picasso.PicassoFactory

object AppModule {

    private val endpoints: Endpoints by lazy { Endpoints(AppEnvironment.current.authConfig, AppEnvironment.current.demoAuthToken) }

    val logger: ILogger by lazy { AndroidLogger() }
    val picassoFactory: PicassoFactory by lazy { PicassoFactory() }
    val gsonFactory: GsonFactory by lazy { GsonFactory() }
    val services: IServices by lazy { Services(endpoints, gsonFactory, logger) }

}