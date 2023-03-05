package com.laioffer.tinnews.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.SwipeNewsCardBinding
import com.laioffer.tinnews.model.Article
import java.lang.reflect.Array.get
import java.util.ArrayList

class CardSwipeAdapter : RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder>() {

    inner class CardSwipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var titleTextView: TextView
        var descriptionTextView: TextView

        init {
            val binding = SwipeNewsCardBinding.bind(itemView)
            imageView = binding.swipeCardImageView
            titleTextView = binding.swipeCardTitle
            descriptionTextView = binding.swipeCardDescription
        }
    }

    private val articles: MutableList<Article> = ArrayList<Article>()

    private val differCallBack = object  : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun setArticles(newsList: List<Article>?) {
        articles.clear()
        articles.addAll(newsList!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSwipeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.swipe_news_card, parent, false)
        return CardSwipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardSwipeViewHolder, position: Int) {
        val article: Article = differ.currentList[position]

        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
        Glide.with(holder.itemView.context).load(article.urlToImage).into(holder.imageView)
    }




    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}