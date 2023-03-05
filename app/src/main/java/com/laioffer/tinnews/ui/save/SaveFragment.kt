package com.laioffer.tinnews.ui.save


import android.content.ContentValues
import com.laioffer.tinnews.ui.save.SaveViewModel
import android.os.Bundle
import android.util.Log
import com.laioffer.tinnews.ui.save.SaveFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.laioffer.tinnews.ui.save.SavedNewsAdapter
import com.laioffer.tinnews.model.Article
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.laioffer.tinnews.repository.NewsRepository
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.FragmentSaveBinding
import com.laioffer.tinnews.repository.NewsViewModelFactory
import com.laioffer.tinnews.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [SaveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
internal class SaveFragment : Fragment(R.layout.fragment_save) {
    lateinit var binding: FragmentSaveBinding
    lateinit var viewModel: SaveViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_save, container, false);
        binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedNewsAdapter = SavedNewsAdapter()
        savedNewsAdapter.setItemCallback(object : SavedNewsAdapter.ItemCallback {
            override fun onOpenDetails(article: Article) {
                // TODO
                Log.d("onOpenDetails", article.toString())
                val direction: SaveFragmentDirections.ActionNavigationSaveToNavigationDetails =
                    SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article)
                NavHostFragment.findNavController(this@SaveFragment).navigate(direction)
            }

            override fun onRemoveFavorite(article: Article) {
                viewModel.deleteSavedArticle(article)
            }


        })
        binding.newsSavedRecyclerView.adapter = savedNewsAdapter
        binding.newsSavedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val repository = NewsRepository()
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository)).get(
            SaveViewModel::class.java
        )

        lifecycleScope.launch {
            viewModel.allSaveArticles()
                .catch { it.printStackTrace() }
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { articles ->
                    savedNewsAdapter.differ.submitList(articles)
                }
        }




    }
}