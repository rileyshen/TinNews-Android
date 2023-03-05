package com.laioffer.tinnews.model

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int,
    val code: String,
    val message: String,
)