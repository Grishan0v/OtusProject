package com.example.otusproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.otusproject.adapter.MovieRecyclerAdapter
import com.example.otusproject.data.App
import com.example.otusproject.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val FAV_ARRAY = "FAV_ARRAY"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var favoriteItems: ArrayList<MovieItem>
    private val items = ArrayList<MovieItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        favoriteItems = arguments?.getParcelableArrayList<MovieItem>(FAV_ARRAY) as ArrayList<MovieItem>

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            binding.dayNightBtn.text = getString(R.string.day)
         else binding.dayNightBtn.text = getString(R.string.night)

        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.api.getMoviesFromDB()
            .enqueue(object: Callback<List<MovieItemModel>> {
                override fun onFailure(call: Call<List<MovieItemModel>>, t: Throwable) {

                }
                override fun onResponse(
                    call: Call<List<MovieItemModel>>,
                    response: Response<List<MovieItemModel>>
                ) {
                    if(response.isSuccessful){
                        response.body()?.forEach {
                            items.add(
                                MovieItem(
                                    it.title,
                                    it.overview,
                                    it.posterPath,
                                    it.voteAverage,
                                    it.releaseDate)
                            )
                        }
                    }
                }
            })
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
                        (activity as? OnRefresh)?.favoritesRefresh(favoriteItems)
                    }
                    return@setOnLongClickListener true
                })
    }

    private fun startDetailsFragment(item: MovieItem) {
        (activity as? OnRefresh)?.startDetailFragment(item)
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
        fun startDetailFragment(item: MovieItem)
    }

}




