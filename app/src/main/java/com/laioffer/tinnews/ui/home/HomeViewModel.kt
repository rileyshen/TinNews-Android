package com.laioffer.tinnews.ui.home

import androidx.lifecycle.*
import com.laioffer.tinnews.repository.NewsRepository
import com.laioffer.tinnews.model.NewsResponse
import com.laioffer.tinnews.model.Article
import com.laioffer.tinnews.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val repository: NewsRepository
) : ViewModel() {

//    val countryInput = MutableLiveData<String>()
//
//
//    fun setCountryInput(country: String) {
//        countryInput.value = country
//    }

    val topHeadlines = MutableStateFlow<Resource<NewsResponse>?>(null)
    val topHeadlinesResult: StateFlow<Resource<NewsResponse>?> = topHeadlines
        .stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    private var breakingNewsResponse: NewsResponse? = null

    init {
        getTopHeadlines("us")
    }

    fun getTopHeadlines(countryCode: String) {

     viewModelScope.launch {

         safeBreakingNewsCall(countryCode)
     } }


    private fun HandleTopHeadlines(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful) {
            response.body()?.let { res ->

                if (breakingNewsResponse == null) {
                    breakingNewsResponse = res
                }else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = res.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(res)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        topHeadlines.value = Resource.Loading()

        when (val response = repository.getTopHeadlines(countryCode)) {
            is Resource.Success -> {
                topHeadlines.value = HandleTopHeadlines(response.data!!)
            }
            is Resource.Error -> {


                topHeadlines.value = null


            }
            else -> {}
        }

    }


    fun setFavoriteArticleInput(article: Article) = viewModelScope.launch{
        repository.favoriteArticle(article)
    }
}