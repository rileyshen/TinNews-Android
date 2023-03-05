package com.laioffer.tinnews.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.FragmentHomeBinding
import com.laioffer.tinnews.model.Article
import com.laioffer.tinnews.model.NewsResponse
import com.laioffer.tinnews.repository.NewsRepository
import com.laioffer.tinnews.repository.NewsViewModelFactory
import com.laioffer.tinnews.ui.details.DetailsFragmentArgs
import com.laioffer.tinnews.util.Resource

import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.internal.CardStackSetting;
import com.yuyakaido.android.cardstackview.internal.CardStackSmoothScroller;
import com.yuyakaido.android.cardstackview.internal.CardStackState;
import com.yuyakaido.android.cardstackview.internal.DisplayUtil;

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//class HomeFragment : Fragment(), CardStackListener {
class HomeFragment : Fragment(R.layout.fragment_home), CardStackListener  {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    lateinit var layoutManager: CardStackLayoutManager
    lateinit var articles: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup Cardstackview
        val cardSwipeAdapter = CardSwipeAdapter()
        layoutManager = CardStackLayoutManager(requireContext(), this)
        layoutManager.setStackFrom(StackFrom.Top)
        binding.homeCardStackView.setLayoutManager(layoutManager)
        binding.homeCardStackView.setAdapter(cardSwipeAdapter)
        val repository = NewsRepository()
        //        viewModel = new HomeViewModel(repository);
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository)).get<HomeViewModel>(
            HomeViewModel::class.java
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.topHeadlinesResult
                        .collectLatest { response ->
                            when (response) {

                                is Resource.Success -> {

                                    response.data?.let { newsResponse ->
                                        cardSwipeAdapter.differ.submitList(newsResponse.articles.toList())



                                    }

                                }
                                else -> {


                                        Toast.makeText(
                                            requireContext(),
                                            "",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                    }

                                }

                            }
                        }
                }

            }



        binding.homeLikeButton.setOnClickListener { v: View? -> swipeCard(Direction.Right) }
        binding.homeUnlikeButton.setOnClickListener { v: View? -> swipeCard(Direction.Left) }
    }
//
    private fun swipeCard(direction: Direction) {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(Duration.Normal.duration)
            .build()
        layoutManager.setSwipeAnimationSetting(setting)
        binding.homeCardStackView.swipe()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}
    override fun onCardSwiped(direction: Direction) {
        if (direction === Direction.Left) {
            Log.d("CardStackView", "Unliked " + layoutManager.getTopPosition())
        } else if (direction === Direction.Right) {
            Log.d("CardStackView", "Liked " + layoutManager.getTopPosition())
            val article: Article = articles!![layoutManager.getTopPosition() - 1]
            viewModel!!.setFavoriteArticleInput(article)
        }
    }

    override fun onCardRewound() {}
    override fun onCardCanceled() {}
    override fun onCardAppeared(view: View?, position: Int) {}
    override fun onCardDisappeared(view: View?, position: Int) {}

}