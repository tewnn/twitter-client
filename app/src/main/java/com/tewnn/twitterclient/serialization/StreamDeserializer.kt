package com.tewnn.twitterclient.serialization

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.tewnn.twitterclient.Constants.Log.tagNetwork
import com.tewnn.twitterclient.logger.ILogger
import com.tewnn.twitterclient.model.TweetAdded
import com.tewnn.twitterclient.model.TweetDeleted
import com.tewnn.twitterclient.model.TweetUpdate
import com.tewnn.twitterclient.model.contract.StreamMessage
import com.tewnn.twitterclient.model.contract.Tweet
import com.tewnn.twitterclient.model.exceptions.DeserializationException
import com.tewnn.twitterclient.model.exceptions.DisconnectException
import com.tewnn.twitterclient.model.exceptions.ServerException

class StreamDeserializer(
        private val gson: Gson,
        private val logger: ILogger
) {
    @Throws(ServerException::class, DeserializationException::class, DisconnectException::class)
    fun deserialize(str: String): TweetUpdate? {
        try {
            val tweet = gson.fromJson(str, Tweet::class.java)
            if (tweet.isComplete) {
                return TweetAdded(tweet)
            }

            val serverMessage = gson.fromJson<StreamMessage>(str, StreamMessage::class.java)
            if (serverMessage.delete != null) {
                return TweetDeleted(serverMessage.delete.status.id)
            }
            if (serverMessage.error != null) {
                throw ServerException(serverMessage.error)
            }
            if (serverMessage.disconnect != null) {
                throw DisconnectException(serverMessage.disconnect)
            }

            logger.d(tagNetwork, "Unhandled message received: $str")
        } catch (ex: JsonSyntaxException) {
            throw DeserializationException("Json syntax error", ex)
        }

        return null
    }
}