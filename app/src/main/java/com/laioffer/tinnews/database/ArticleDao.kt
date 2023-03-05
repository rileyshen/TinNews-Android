package com.laioffer.tinnews.database

import com.laioffer.tinnews.model.Article
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun allArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}