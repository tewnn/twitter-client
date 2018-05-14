package com.tewnn.twitterclient.ui

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

abstract class UIView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr), IUIView {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            getChildAt(i)?.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = width
        val height = height

        for (i in 0 until childCount) {
            getChildAt(i)?.layout(0, 0, width, height)
        }
    }

    override fun showToast(text: CharSequence) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    protected fun inflate(@LayoutRes layoutResId: Int): View = View.inflate(context, layoutResId, this)

}