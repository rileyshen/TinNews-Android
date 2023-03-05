package com.laioffer.tinnews.repository

import com.laioffer.tinnews.repository.NewsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laioffer.tinnews.ui.save.SaveViewModel

import com.laioffer.tinnews.ui.home.HomeViewModel
import com.laioffer.tinnews.ui.search.SearchViewModel



import java.lang.IllegalStateException

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SaveViewModel::class.java)) {
            SaveViewModel(repository) as T
        } else {
            throw IllegalStateException("Unknown ViewModel")
        }
    }
}