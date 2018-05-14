package com.tewnn.twitterclient.adapters

import android.content.Context
import android.net.Uri
import android.support.annotation.CallSuper
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tewnn.twitterclient.AppModule
import com.tewnn.twitterclient.R
import kotlinx.android.extensions.LayoutContainer

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    private val imageViews: MutableSet<ImageView> = mutableSetOf()

    override val containerView: View? get() = itemView
    protected val context: Context get() = itemView.context
    protected val picasso: Picasso get() = AppModule.picassoFactory.get(context)

    @CallSuper
    open fun recycle() {
        imageViews.forEach { imageView ->
            picasso.cancelRequest(imageView)
            imageView.setImageResource(R.drawable.ic_placeholder)
        }
    }

    protected fun ImageView.loadPicture(uri: Uri?, @DrawableRes placeholderResId: Int = R.drawable.ic_placeholder) {
        if (uri != null) {
            imageViews.add(this)
            picasso.load(uri)
                    .placeholder(placeholderResId)
                    .into(this)
        } else {
            this.setImageResource(placeholderResId)
        }
    }
}