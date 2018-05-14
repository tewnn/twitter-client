package com.tewnn.twitterclient.adapters.tweets

import android.content.Context
import android.view.View
import com.tewnn.twitterclient.R
import com.tewnn.twitterclient.adapters.BaseAdapter
import com.tewnn.twitterclient.adapters.IViewType
import com.tewnn.twitterclient.model.contract.Tweet

class TweetsListAdapter(context: Context) : BaseAdapter<TweetViewHolder, TweetsListAdapter.ViewType>(context, ViewType.values()) {
    enum class ViewType(
            override val layoutId: Int
    ) : IViewType {
        Tweet(R.layout.item_tweet)
    }

    var items: List<Tweet> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun getViewType(position: Int): ViewType = ViewType.Tweet

    override fun onCreateViewHolder(itemView: View, viewType: ViewType): TweetViewHolder = TweetViewHolder(itemView)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) = holder.bind(getTweet(position))

    override fun getItemId(position: Int): Long = getTweet(position).id

    private fun getTweet(position: Int) = items[position]
}