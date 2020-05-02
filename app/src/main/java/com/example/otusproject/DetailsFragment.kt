package com.example.otusproject

import android.os.Bundle
import android.text.Editable
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
    private lateinit var favoriteItems: ArrayList<MovieItem>
    private var movieItem: MovieItem? = null
    private lateinit var note: Editable
    private var noteText = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        retainInstance = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.collapsingToolbar
        toolbar!!.title = "Details"

        movieItem = arguments?.getParcelable(ITEM)
        favoriteItems = arguments?.getParcelableArrayList<MovieItem>("FAV_ARRAY") as ArrayList<MovieItem>

        if (favoriteItems.contains(movieItem))
            binding.checkBox.isChecked = true

        val toolbarImg = binding.moviePoster
        val title = binding.title
        val movieDate = binding.movieReleaseDate
        val starringActors = binding.starringActors
        val director = binding.director
        val rating = binding.rating
        val description = binding.description

        Glide.with(view.context)
            .load(movieItem?.moviePoster)
            .into(toolbarImg!!)

        title.text = movieItem?.title
        movieDate.text = movieItem?.year
        starringActors.text = movieItem?.starring
        director.text = movieItem?.director
        rating.text = movieItem?.rating
        description.text = movieItem?.description
    }

    fun newInstance (favoriteItems: ArrayList<MovieItem>, item: MovieItem) =
        DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(FAV_ARRAY, favoriteItems)
                putParcelable(ITEM, item)
            }
        }

}
