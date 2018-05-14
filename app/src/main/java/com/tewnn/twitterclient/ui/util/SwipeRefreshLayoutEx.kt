package com.tewnn.twitterclient.ui.util

import android.support.v4.widget.SwipeRefreshLayout
import io.reactivex.Observable

fun SwipeRefreshLayout.refreshes(): Observable<Unit> =
        Observable.create<Unit> { emitter ->
            this.setOnRefreshListener {
                if (!emitter.isDisposed) {
                    emitter.onNext(Unit)
                }
            }
            emitter.setCancellable { this.setOnRefreshListener(null) }
        }