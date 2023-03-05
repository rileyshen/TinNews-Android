package com.laioffer.tinnews.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.SearchNewsItemBinding
import com.laioffer.tinnews.model.Article


import java.util.ArrayList

class SearchNewsAdapter : RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder?>() {
    private val differCallBack = object  : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    interface ItemCallback {
        fun onOpenDetails(article: Article)
    }

    private lateinit var itemCallback: ItemCallback

    fun setItemCallback(itemCallback: ItemCallback) {
        this.itemCallback = itemCallback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchNewsViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.search_news_item, parent, false)
        return SearchNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchNewsViewHolder, position: Int) {
        val article: Article = differ.currentList[position]
        holder.favoriteImageView?.setImageResource(R.drawable.ic_favorite_24)
        holder.itemTitleTextView.setText(article.title)
        Glide.with(holder.itemView.getContext()).load(article.urlToImage).override(200, 200).into(holder.itemImageView)
        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
            itemCallback!!.onOpenDetails(
                article
            )
        })
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class SearchNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var favoriteImageView: ImageSwitcher? = null
        var itemImageView: ImageView
        var itemTitleTextView: TextView

        init {
            val binding = SearchNewsItemBinding.bind(itemView)
            itemImageView = binding.searchItemImage
            itemTitleTextView = binding.searchItemTitle
        }
    }


}