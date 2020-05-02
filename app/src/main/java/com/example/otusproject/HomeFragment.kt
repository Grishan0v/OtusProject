package com.example.otusproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.otusproject.adapter.MovieRecyclerAdapter
import com.example.otusproject.databinding.FragmentHomeBinding

private const val FAV_ARRAY = "FAV_ARRAY"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var favoriteItems: ArrayList<MovieItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        favoriteItems = arguments?.getParcelableArrayList<MovieItem>(FAV_ARRAY) as ArrayList<MovieItem>

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            binding.dayNightBtn.text = getString(R.string.day)
         else binding.dayNightBtn.text = getString(R.string.night)

        retainInstance = true

        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        binding.dayNightBtn.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                binding.dayNightBtn.text = getString(R.string.night)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                binding.dayNightBtn.text = getString(R.string.day)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewId.addItemDecoration(SpacingItemDecoration(30))
        val items = createMovieSet()

        binding.recyclerViewId.adapter =
            MovieRecyclerAdapter(
                LayoutInflater.from(
                    activity
                ), items, setOnClickListener@{
                    startDetailsFragment(it)
                }, setOnLongClickListener@{
                    if (favoriteItems.contains(it))
                        showToastMessage(getString(R.string.toasAlreadyInFav))
                    else {
                        favoriteItems.add(it)
                        showToastMessage(getString(R.string.toastAddedToFav))
                        (activity as MainActivity).favoritesRefresh(favoriteItems)
                    }
                    return@setOnLongClickListener true
                })
    }

    private fun startDetailsFragment(item: MovieItem) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_home_screen, DetailsFragment().newInstance(favoriteItems, item))
            addToBackStack("transfer")
            commit()
        }
    }

    private fun createMovieSet():  ArrayList<MovieItem> {
        val movieList = ArrayList<MovieItem>()

        movieList.add(
            MovieItem(
                getString(R.string.movieName1),
                getString(R.string.movie1Description1),
                "https://m.media-amazon.com/images/M/MV5BNzA1Njg4NzYxOV5BMl5BanBnXkFtZTgwODk5NjU3MzI@._V1_SY1000_CR0,0,674,1000_AL_.jpg",
                getString(R.string.ratingMovie1),
                getString(R.string.dateMovie1),
                getString(R.string.directorMovie1),
                getString(R.string.actorsMovie1)
            )
        )
        movieList.add(
            MovieItem(
                getString(R.string.movieName2),
                getString(R.string.movie1Description2),
                "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SY1000_CR0,0,674,1000_AL_.jpg",
                getString(R.string.ratingMovie2),
                getString(R.string.dateMovie2),
                getString(R.string.directorMovie2),
                getString(R.string.actorsMovie2)
            )
        )
        movieList.add(
            MovieItem(
                getString(R.string.movieName3),
                getString(R.string.movie1Description3),
                "https://m.media-amazon.com/images/M/MV5BMGUwZjliMTAtNzAxZi00MWNiLWE2NzgtZGUxMGQxZjhhNDRiXkEyXkFqcGdeQXVyNjU1NzU3MzE@._V1_SY1000_SX675_AL_.jpg",
                getString(R.string.ratingMovie3),
                getString(R.string.dateMovie3),
                getString(R.string.directorMovie3),
                getString(R.string.actorsMovie1)
            )
        )
        return movieList
    }

    private fun showToastMessage(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT )
        toast.show()
    }

    fun newInstance (favoriteItems: ArrayList<MovieItem>) =
        HomeFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(FAV_ARRAY, favoriteItems)
            }
        }

    interface OnRefresh {
        fun favoritesRefresh(favorites: ArrayList<MovieItem>)
    }
}




