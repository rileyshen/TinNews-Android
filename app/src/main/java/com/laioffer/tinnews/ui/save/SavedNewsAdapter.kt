package com.laioffer.tinnews.ui.save

import androidx.recyclerview.widget.RecyclerView
import com.laioffer.tinnews.ui.save.SavedNewsAdapter.SavedNewsViewHolder
import com.laioffer.tinnews.model.Article
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.laioffer.tinnews.R
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.laioffer.tinnews.databinding.SavedNewsItemBinding
import java.util.ArrayList

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsViewHolder>() {

    private val differCallBack = object  : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    private val articles: MutableList<Article> = ArrayList()
    fun setArticles(newsList: List<Article>?) {
        articles.clear()
        articles.addAll(newsList!!)

    }

    interface ItemCallback {
        fun onOpenDetails(article: Article)
        fun onRemoveFavorite(article: Article)
    }

    private var itemCallback: ItemCallback? = null
    fun setItemCallback(itemCallback: ItemCallback?) {
        this.itemCallback = itemCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.saved_news_item, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.authorTextView.text = article.author
        holder.descriptionTextView.text = article.description
        holder.favoriteIcon.setOnClickListener { v: View? -> itemCallback!!.onRemoveFavorite(article) }
        holder.itemView.setOnClickListener { v: View? -> itemCallback!!.onOpenDetails(article) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SavedNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorTextView: TextView
        var descriptionTextView: TextView
        var favoriteIcon: ImageView

        init {
            val binding = SavedNewsItemBinding.bind(itemView)
            authorTextView = binding.savedItemAuthorContent
            descriptionTextView = binding.savedItemDescriptionContent
            favoriteIcon = binding.savedItemFavoriteImageView
        }
    }
}