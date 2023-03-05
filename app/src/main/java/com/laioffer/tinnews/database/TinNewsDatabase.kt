package com.laioffer.tinnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.laioffer.tinnews.model.Article
import androidx.room.RoomDatabase
import com.laioffer.tinnews.database.ArticleDao

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class TinNewsDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: TinNewsDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TinNewsDatabase::class.java,
                "tinnews_db"
            ).build()
    }
}