package com.laioffer.tinnews.ui.search


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.laioffer.tinnews.repository.NewsRepository
import com.laioffer.tinnews.repository.NewsViewModelFactory
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.FragmentSearchBinding
import com.laioffer.tinnews.util.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
internal class SearchFragment : Fragment(R.layout.fragment_search) {
     lateinit var viewModel: SearchViewModel
     lateinit  var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false);
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //

////        viewModel.setSearchInput("Covid-19");
        val newsAdapter = SearchNewsAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.newsResultsRecyclerView.layoutManager = gridLayoutManager
        binding.newsResultsRecyclerView.setAdapter(newsAdapter)
        binding.newsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isEmpty()) {
                    viewModel.searchNews(query)
                }
                binding.newsSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        val repository = NewsRepository()
        //        viewModel= new SearchViewModel(repository);
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository)).get(
            SearchViewModel::class.java
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchNewsResult
                    .collectLatest { response ->
                        when (response) {

                            is Resource.Success -> {

                                response.data.let { newsResponse ->
                                    newsAdapter.differ.submitList(newsResponse!!.articles.toList())

                                }
                            }
                            else -> {


                                    Toast.makeText(
                                        activity,
                                        "An Error occurred: ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                    }
            }
        }



    }