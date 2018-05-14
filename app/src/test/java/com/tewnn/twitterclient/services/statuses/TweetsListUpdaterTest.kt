package com.tewnn.twitterclient.services.statuses

import com.tewnn.twitterclient.TestDataGenerator.generateTweet
import com.tewnn.twitterclient.TestDataGenerator.generateTweets
import com.tewnn.twitterclient.model.TweetAdded
import com.tewnn.twitterclient.model.TweetDeleted
import org.junit.Test

class TweetsListUpdaterTest {

    private val target: TweetsListUpdater = TweetsListUpdater(100)

    @Test
    fun `add to empty list`() {
        val added = generateTweets(5)
        val updated = target.applyUpdates(emptyList(), added.map { TweetAdded(it) })
        assert(added == updated)
    }

    @Test
    fun `add to non-empty list`() {
        val initial = generateTweets(5)
        val added = generateTweets(2)
        val updated = target.applyUpdates(initial, added.map { TweetAdded(it) })
        assert(initial + added == updated)
    }

    @Test
    fun `delete from empty list`() {
        val updated = target.applyUpdates(emptyList(), generateTweets(2).map { TweetDeleted(it.id) })
        assert(updated.isEmpty())
    }

    @Test
    fun `delete of non-existing items`() {
        val initial = generateTweets(5)
        val deleted = generateTweets(2)
        val updated = target.applyUpdates(initial, deleted.map { TweetDeleted(it.id) })
        assert(updated == initial)
    }

    @Test
    fun `delete of existing items`() {
        val initial = generateTweets(5)
        val deleted = initial.subList(1, 3)
        val updated = target.applyUpdates(initial, deleted.map { TweetDeleted(it.id) })
        assert(updated == initial - deleted)
    }

    @Test
    fun `add more then limit`() {
        val added = generateTweets(110)
        val updated = target.applyUpdates(emptyList(), added.map { TweetAdded(it) })
        assert(updated == added.subList(0, target.tweetsListMaxSize))
    }

    @Test
    fun `add and delete`() {
        val tweet = generateTweet()
        val updates = listOf(TweetAdded(tweet), TweetDeleted(tweet.id))
        val updated = target.applyUpdates(emptyList(), updates)
        assert(updated.isEmpty())
    }

    @Test
    fun `delete and add`() {
        val tweet = generateTweet()
        val updates = listOf(TweetDeleted(tweet.id), TweetAdded(tweet))
        val updated = target.applyUpdates(emptyList(), updates)
        assert(updated == listOf(tweet))
    }
}