package com.laioffer.tinnews.ui.search

import androidx.lifecycle.*
import com.laioffer.tinnews.repository.NewsRepository
import com.laioffer.tinnews.model.NewsResponse
import com.laioffer.tinnews.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel  @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val searchNews =  MutableStateFlow<Resource<NewsResponse>?>(null)

    val searchNewsResult: StateFlow<Resource<NewsResponse>?> = searchNews.asStateFlow()
        .stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )


    var searchNewsPage = 1


    private var searchNewsResponse: NewsResponse? = null

    fun searchNews(searchQuery: String) = viewModelScope.launch {


        safeSearchNewsCall(searchQuery)
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        searchNews.value = Resource.Loading()
            when (val response = repository.searchNews(searchQuery, searchNewsPage)) {
                is Resource.Success -> {
                    searchNews.value = handleSearchNewsResponse(response.data!!)
                }
                is Resource.Error -> {


                    searchNews.value = Resource.Error("Conversion Error")


                    }

                else -> {}
            }

        }

}