package com.tewnn.twitterclient.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<VH : BaseViewHolder, VT : IViewType>(
        val context: Context,
        val viewTypes: Array<VT>
) : RecyclerView.Adapter<VH>() {

    final override fun getItemViewType(position: Int): Int = getViewType(position).ordinal

    final override fun onCreateViewHolder(parent: ViewGroup, viewTypeIndex: Int): VH {
        val viewType = viewTypes[viewTypeIndex]
        val itemView = LayoutInflater.from(context).inflate(viewType.layoutId, parent, false)
        return onCreateViewHolder(itemView, viewType)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    abstract fun getViewType(position: Int): VT
    abstract fun onCreateViewHolder(itemView: View, viewType: VT): VH
}