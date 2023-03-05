package com.laioffer.tinnews.network

import com.laioffer.tinnews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        countryCode: String = "us",
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>
}



//interface NewsApi {
//    @GET("top-headlines")
//    fun getTopHeadlines(@Query("country") country: String?): Response<NewsResponse?>?
//
//    @GET("everything")
//    fun getEverything(
//        @Query("q") query: String?, @Query("pageSize") pageSize: Int
//    ):Response<NewsResponse?>?
//}
