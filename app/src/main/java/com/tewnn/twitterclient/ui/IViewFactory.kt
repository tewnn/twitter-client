package com.tewnn.twitterclient.ui

interface IViewFactory {
    fun <View : IUIView> createView(presenter: UIPresenter<View>): View
}