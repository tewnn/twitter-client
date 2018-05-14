package com.tewnn.twitterclient.ui

interface IUIParent {
    val currentPresenter: UIPresenter<IUIView>?

    fun <View : IUIView> pushPresenter(presenter: UIPresenter<View>)
    fun popPresenter()
    fun <View : IUIView> replacePresenter(presenter: UIPresenter<View>)
    fun <View : IUIView> replacePresenter(presenter: UIPresenter<View>, replacingPresenter: UIPresenter<View>?)
}