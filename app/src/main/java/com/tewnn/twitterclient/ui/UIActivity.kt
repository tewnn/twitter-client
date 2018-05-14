package com.tewnn.twitterclient.ui

import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.tewnn.twitterclient.AppModule
import com.tewnn.twitterclient.R
import com.tewnn.twitterclient.ui.util.TreadUtil.checkThread
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.ui_activity.*
import java.util.*

@MainThread
abstract class UIActivity : AppCompatActivity(), IUIParent {
    override val currentPresenter get() = presentersStack.lastOrNull()

    private val presentersStack: MutableList<UIPresenter<IUIView>> = LinkedList()
    private val viewFactory: IViewFactory by lazy { ViewFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_activity)
    }

    override fun onStart() {
        super.onStart()
        currentPresenter?.callSubscribeIfNotSubscribed()
    }

    override fun onResume() {
        super.onResume()
        currentPresenter?.callSubscribeIfNotSubscribed()
    }

    override fun onStop() {
        super.onStop()
        currentPresenter?.callUnsubscribe()
    }

    override fun onBackPressed() {
        if (currentPresenter?.onBackPressed() != true) {
            popPresenter()
        }
    }

    override fun <View : IUIView> pushPresenter(presenter: UIPresenter<View>) {
        pushPresenter(presenter, null)
    }

    override fun <View : IUIView> replacePresenter(presenter: UIPresenter<View>) {
        pushPresenter(presenter, currentPresenter)
    }

    override fun <View : IUIView> replacePresenter(presenter: UIPresenter<View>, replacingPresenter: UIPresenter<View>?) {
        pushPresenter(presenter, replacingPresenter)
    }

    override fun popPresenter() {
        val oldPresenter = currentPresenter
        presentersStack.remove(oldPresenter)
        val newPresenter = presentersStack.lastOrNull()
        if (newPresenter != null) {
            replaceViews(oldPresenter, newPresenter)
        } else {
            finish()
        }
    }

    private fun <View : IUIView> pushPresenter(presenter: UIPresenter<View>, replacingPresenter: UIPresenter<View>?) {
        checkThread()
        val oldPresenter = currentPresenter

        replacingPresenter?.let {
            while (presentersStack.contains(replacingPresenter)) {
                presentersStack.remove(presentersStack.last())
            }
        }

        if (presentersStack.add(presenter)) {
            replaceViews(oldPresenter, presenter)
        }
    }

    private fun <View : IUIView> replaceViews(oldPresenter: UIPresenter<View>?, newPresenter: UIPresenter<View>) {
        checkThread()
        oldPresenter?.callUnsubscribe()
        newPresenter.init(this, viewFactory, AppModule.services, AndroidSchedulers.mainThread(), AppModule.logger)
        mainContainer.removeAllViews()
        oldPresenter?.detachView()
        mainContainer.addView(newPresenter.view as android.view.View, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        newPresenter.callSubscribeIfNotSubscribed()
    }
}