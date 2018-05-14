package com.tewnn.twitterclient

import com.tewnn.twitterclient.config.AuthConfig
import com.tewnn.twitterclient.config.AuthToken
import com.tewnn.twitterclient.config.EndpointsConfig

enum class AppEnvironment {
    Develop;

    companion object {
        val current: AppEnvironment
            get() = when (BuildConfig.BUILD_TYPE) {
                "debug" -> Develop
                else -> throw NotImplementedError("Unknown app environment for build type ${BuildConfig.BUILD_TYPE}")
            }
    }
}

val AppEnvironment.authConfig: AuthConfig
    get() = when (this) {
        AppEnvironment.Develop -> AuthConfig(TODO("add your api key"), TODO("add your api secret"))
    }

val AppEnvironment.demoAuthToken: AuthToken
    get() = when (this) {
        AppEnvironment.Develop -> AuthToken(TODO("add your access token"), TODO("add your acces token secret"))
    }

val AppEnvironment.endpointsConfig: EndpointsConfig
    get() = when (this) {
        AppEnvironment.Develop -> EndpointsConfig("stream.twitter.com")
    }