package com.laioffer.tinnews.network

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

object RetrofitClient {
    private const val API_KEY = "a5e45812b61f46ca96f72da9ce3da6cd"
    private const val BASE_URL = "https://newsapi.org/v2/"


    fun newInstance(): Retrofit {
        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val request = original
                .newBuilder()
                .header("X-Api-Key", API_KEY)
                .build()
            return chain.proceed(request)
        }
    }
}