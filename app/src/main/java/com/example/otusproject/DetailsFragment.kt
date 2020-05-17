package com.example.otusproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.otusproject.databinding.FragmentDetailsBinding

private const val FAV_ARRAY = "FAV_ARRAY"
private const val ITEM = "MOVIE_ITEM"

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var favoriteItems: ArrayList<Result>
    private var movieItem: Result? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteItems = ArrayList()

        val toolbar = binding.collapsingToolbar
        toolbar!!.title = "Details"

        movieItem = arguments?.getParcelable(ITEM)

        if (favoriteItems.contains(movieItem))
            binding.checkBox.isChecked = true

        val toolbarImg = binding.moviePoster
        val title = binding.title
        val movieDate = binding.movieReleaseDate
        val rating = binding.rating
        val description = binding.description

        movieItem?.let {
            Glide.with(view.context)
                .load("https://image.tmdb.org/t/p/w500" + it.posterPath)
                .into(toolbarImg!!)

            title.text = it.title
            movieDate.text = it.releaseDate
            rating.text = it.voteAverage.toString()
            description.text = it.overview
        }
    }

    fun newInstance(favoriteItems: ArrayList<Result>, item: Result) =
        DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(FAV_ARRAY, favoriteItems)
                putParcelable(ITEM, item)
            }
        }

}
