package com.tewnn.twitterclient.ui.tweets

import com.nhaarman.mockito_kotlin.*
import com.tewnn.twitterclient.TestDataGenerator.generateTweets
import com.tewnn.twitterclient.TestLogger
import com.tewnn.twitterclient.model.contract.StreamDisconnect
import com.tewnn.twitterclient.model.contract.StreamError
import com.tewnn.twitterclient.model.exceptions.DeserializationException
import com.tewnn.twitterclient.model.exceptions.DisconnectException
import com.tewnn.twitterclient.model.exceptions.NetworkException
import com.tewnn.twitterclient.model.exceptions.ServerException
import com.tewnn.twitterclient.services.IServices
import com.tewnn.twitterclient.services.statuses.IStatusesService
import com.tewnn.twitterclient.ui.IUIParent
import com.tewnn.twitterclient.ui.IViewFactory
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class TweetsListPresenterTest {

    private val statusesService: IStatusesService = mock()
    private val services: IServices = mock {
        on { statusesService } doReturn statusesService
    }
    private val uiParent: IUIParent = mock()
    private val view: ITweetsListView = mock {
        on { refreshes } doReturn Observable.never()
        on { resubscribes } doReturn Observable.never()
    }
    private val viewFactory: IViewFactory = mock {
        on { createView(any<TweetsListPresenter>()) } doReturn view
    }
    private val scheduler = TestScheduler()

    private lateinit var target: TweetsListPresenter

    @Before
    fun before() {
        target = TweetsListPresenter()
        target.init(uiParent, viewFactory, services, scheduler, TestLogger)
    }

    @Test
    fun `set tweets to the view`() {
        val items = generateTweets(5)
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.just(items))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).setTweets(items)
    }

    @Test
    fun `update tweets to the view`() {
        val items1 = generateTweets(5)
        val items2 = generateTweets(8)
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.fromArray(items1, items2))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).setTweets(items1)
        verify(view).setTweets(items2)
    }

    @Test
    fun `network error occurs`() {
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.error(NetworkException("")))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).showStreamErrorMessage(ITweetsListView.StreamError.NoInternetConnection)
    }

    @Test
    fun `server error occurs`() {
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.error(ServerException(StreamError(null, null))))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).showStreamErrorMessage(ITweetsListView.StreamError.ServerError)
    }

    @Test
    fun `stream disconnected`() {
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.error(DisconnectException(StreamDisconnect(1, null, null))))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).showStreamErrorMessage(ITweetsListView.StreamError.StreamDisconnected)
    }

    @Test
    fun `parsing error occurs`() {
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.error(DeserializationException("")))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).showStreamErrorMessage(ITweetsListView.StreamError.ProtocolProblem)
    }

    @Test
    fun `unknown error occurs`() {
        whenever(statusesService.sampleStatusesStream).doReturn(Observable.error(RuntimeException()))
        target.callSubscribeIfNotSubscribed()
        scheduler.triggerActions()
        verify(view).showStreamErrorMessage(ITweetsListView.StreamError.Unknown)
    }
}
