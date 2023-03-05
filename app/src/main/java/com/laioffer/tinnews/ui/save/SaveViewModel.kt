package com.laioffer.tinnews.ui.save

import com.laioffer.tinnews.repository.NewsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.laioffer.tinnews.model.Article
import com.laioffer.tinnews.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveViewModel  @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _resultSharedFlow = MutableSharedFlow<Resource.StringResource<Int>>()
    val resultSharedFlow = _resultSharedFlow.asSharedFlow()

    fun allSaveArticles() : Flow<List<Article>> = repository.allSavedArticles()

    fun deleteSavedArticle(article: Article) = viewModelScope.launch{
        repository.deleteSavedArticle(article)
    }

    fun savedArticle(article: Article) = viewModelScope.launch{
        repository.favoriteArticle(article)
    }
}