package com.tewnn.twitterclient.ui.picasso

import android.content.Context
import com.squareup.picasso.Picasso

class PicassoFactory {
    fun get(context: Context): Picasso = Picasso.get()
}