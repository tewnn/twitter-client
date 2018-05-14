package com.tewnn.twitterclient

import com.tewnn.twitterclient.model.contract.Tweet
import com.tewnn.twitterclient.model.contract.User
import java.util.concurrent.atomic.AtomicLong

object TestDataGenerator {

    private val idCounter = AtomicLong(0L)

    fun generateId(): Long = idCounter.incrementAndGet()

    fun generateTweets(count: Int): List<Tweet> {
        return (0 until count).map { generateTweet() }
    }

    fun generateTweet(): Tweet {
        val id = generateId()
        return Tweet(id, id.toString(), "Text", generateUser(), null, null, null)
    }

    fun generateUser(): User {
        val id = generateId()
        return User(id, "I.C.Winner", "@ICWinner", null)
    }
}