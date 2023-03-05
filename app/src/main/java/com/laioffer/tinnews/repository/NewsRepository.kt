package com.laioffer.tinnews.repository


import androidx.room.Database

import com.laioffer.tinnews.TinNewsApplication
import com.laioffer.tinnews.network.NewsApi

import com.laioffer.tinnews.model.NewsResponse
import com.laioffer.tinnews.database.ArticleDao
import com.laioffer.tinnews.database.TinNewsDatabase


import com.laioffer.tinnews.model.Article
import com.laioffer.tinnews.network.RetrofitClient
import com.laioffer.tinnews.util.Resource

import kotlinx.coroutines.flow.Flow

import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


class NewsRepository (

){

        private val newsApi: NewsApi = RetrofitClient.newInstance().create(NewsApi::class.java)
        private val db: TinNewsDatabase


        init {
                db = TinNewsApplication.database!!
        }

        suspend fun getTopHeadlines(countryCode : String) : Resource<Response<NewsResponse>> {
                return try {
                        val data = newsApi.getTopHeadlines(countryCode)
                        Resource.Success(data)
                } catch (e: HttpException) {
                        Resource.Error(e.toString())
                }
        }



        suspend fun searchNews(query: String, pageNumber: Int) : Resource<Response<NewsResponse>> {
                return try {
                        val data = newsApi.getEverything(query, pageNumber)
                        Resource.Success(data)
                } catch (e: HttpException) {
                        Resource.Error(e.toString())
                }
        }




        fun allSavedArticles() : Flow<List<Article>> = db.getArticleDao().allArticles()


        suspend fun deleteSavedArticle(article: Article) = db.getArticleDao().deleteArticle(article)

        suspend fun favoriteArticle(article: Article) = db.getArticleDao().saveArticle(article)
}



//class NewsRepository {
//    private val newsApi: NewsApi
//    private val database: TinNewsDatabase?
//
//    init {
//        newsApi = newInstance().create(NewsApi::class.java)
//        database = TinNewsApplication.database
//    }
//
//    suspend fun getTopHeadlines(country: String?): Flow<NewsResponse> {
//        val topHeadlinesLiveData = Flow<NewsResponse>()
//        newsApi.getTopHeadlines(country)
//            ?.enqueue(object : Callback<NewsResponse?> {
//                override fun onResponse(
//                    call: Call<NewsResponse?>,
//                    response: Response<NewsResponse?>
//                ) {
//                    if (response.isSuccessful) {
//                        topHeadlinesLiveData.setValue(response.body())
//                    } else {
//                        topHeadlinesLiveData.setValue(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
//                    topHeadlinesLiveData.setValue(null)
//                }
//            })
//        return topHeadlinesFlow
//    }
//
//
//
//
//    suspend fun searchNews(query: String?): LiveData<NewsResponse?> {
//        val everyThingLiveData = MutableLiveData<NewsResponse?>()
//        newsApi.getEverything(query, 40)
//            ?.enqueue(
//                object : Callback<NewsResponse?> {
//                    override fun onResponse(
//                        call: Call<NewsResponse?>,
//                        response: Response<NewsResponse?>
//                    ) {
//                        if (response.isSuccessful) {
//                            everyThingLiveData.setValue(response.body())
//                        } else {
//                            everyThingLiveData.setValue(null)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
//                        everyThingLiveData.setValue(null)
//                    }
//                })
//        return everyThingLiveData
//    }
//
////    private class FavoriteAsyncTask(
////        private val database: TinNewsDatabase?,
////        private val liveData: MutableLiveData<Boolean>
////    ) : AsyncTask<Article?, Void?, Boolean>() {
////        override fun onPreExecute() {
////            super.onPreExecute()
////        }
////
////        protected override fun doInBackground(vararg articles: Article): Boolean {
////            val article = articles[0]
////            try {
////                database!!.articleDao().saveArticle(article)
////            } catch (e: Exception) {
////                return false
////            }
////            return true
////        }
////
////        protected override fun onProgressUpdate(vararg values: Void) {
////            super.onProgressUpdate(*values)
////        }
////
////        override fun onPostExecute(success: Boolean) {
////            liveData.value = success
////        }
////    }
//
//     suspend fun favoriteArticle(article: Article?): LiveData<Boolean> {
//        val resultLiveData = MutableLiveData<Boolean>()
//        FavoriteAsyncTask(database, resultLiveData).execute(article)
//        return resultLiveData
//    }
//
//    val allSavedArticles: LiveData<List<Article>>
//        get() = database!!.articleDao().allArticles()
//
//    suspend fun deleteSavedArticle(article: Article?) {
//        AsyncTask.execute { database!!.articleDao().deleteArticle(article) }
//    }
//}