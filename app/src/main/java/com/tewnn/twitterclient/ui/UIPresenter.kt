package com.tewnn.twitterclient.ui

import android.support.annotation.CallSuper
import com.tewnn.twitterclient.logger.ILogger
import com.tewnn.twitterclient.services.IServices
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UIPresenter<out View : IUIView> {

    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private var isSubscribed: Boolean = false
    private var viewField: View? = null

    val view: View
        get() {
            if (viewField == null) {
                viewField = viewFactory.createView(this)
            }
            return viewField!!
        }
    val viewIfExist: View? get() = viewField

    protected lateinit var uiParent: IUIParent
    protected lateinit var viewFactory: IViewFactory
    protected lateinit var services: IServices
    protected lateinit var scheduler: Scheduler
    protected lateinit var logger: ILogger

    fun init(uiParent: IUIParent, viewFactory: IViewFactory, services: IServices, scheduler: Scheduler, logger: ILogger) {
        this.uiParent = uiParent
        this.viewFactory = viewFactory
        this.services = services
        this.scheduler = scheduler
        this.logger = logger
    }

    fun callSubscribeIfNotSubscribed() {
        if (!isSubscribed) {
            isSubscribed = true
            subscribe()
        }
    }

    fun callUnsubscribe() {
        unsubscribe()
        isSubscribed = false
    }

    fun detachView() {
        viewField = null
    }

    open fun onBackPressed(): Boolean = false

    @CallSuper
    protected open fun subscribe() = Unit

    @CallSuper
    protected open fun unsubscribe() {
        subscriptions.dispose()
    }

    protected fun <T : Disposable> T.register(): T = this.apply {
        if (!isSubscribed) {
            throw IllegalStateException()
        }
        subscriptions.add(this)
    }

    protected open fun close() = uiParent.popPresenter()
}