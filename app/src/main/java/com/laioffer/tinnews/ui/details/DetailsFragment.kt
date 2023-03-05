package com.laioffer.tinnews.ui.details

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.laioffer.tinnews.R
import com.laioffer.tinnews.databinding.FragmentDetailsBinding
import com.laioffer.tinnews.model.Article

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var binding: FragmentDetailsBinding
//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DetailsFragmentArgs by navArgs()
        val article = args.article
        binding.detailsTitleTextView.setText(article.title)
        binding.detailsAuthorTextView.setText(article.author)
        binding.detailsDateTextView.setText(article.publishedAt)
        binding.detailsDescriptionTextView.setText(article.description)
        binding.detailsContentTextView.setText(article.content)

        Glide.with(this).load(article.urlToImage).into(binding.detailsImageView)
    }
}