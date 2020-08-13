package com.example.otusproject.ui.screen_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.otusproject.R
import com.example.otusproject.data.vo.Movie
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {
    private var viewModel: HomeFragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        viewModel!!.selectedMovie.observe(this.viewLifecycleOwner, Observer {
                selectedMovie ->

        val toolbar = collapsingToolbar
        toolbar!!.title = "Details"

        val toolbarImg = moviePoster
        val title = title
        val movieDate = movieReleaseDate
        val rating = rating
        val description = description

        selectedMovie?.let {
            Glide.with(view.context)
                .load("https://image.tmdb.org/t/p/w500" + it.posterPath)
                .into(toolbarImg!!)

            title.text = it.title
            movieDate.text = it.releaseDate
            rating.text = it.voteAverage.toString()
            description.text = it.overview
        }
        })
    }
}
