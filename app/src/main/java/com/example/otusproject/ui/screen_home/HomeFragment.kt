package com.example.otusproject.ui.screen_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.otusproject.R
import com.example.otusproject.data.vo.Movie
import com.example.otusproject.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val items = mutableListOf<Movie>()
    private var viewModel: HomeFragmentViewModel? = null
    private var adapter: MovieRecyclerAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeBtnText()
        initRecyclerView()

        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        viewModel!!.initMovieList()
        viewModel!!.initFavList()
        viewModel!!.movies.observe(this.viewLifecycleOwner, Observer { movies -> adapter?.setItems(movies)})
        viewModel!!.error.observe(this.viewLifecycleOwner, Observer { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show()})

        dayNightBtn.setOnClickListener {viewModel!!.onThemeChange()}
    }

    private fun initRecyclerView() {
       recycler_view_id.addItemDecoration(SpacingItemDecoration(30))
        adapter = MovieRecyclerAdapter(LayoutInflater.from(activity), items as ArrayList<Movie>,
            setOnClickListener@{
                viewModel!!.onMovieSelect(it)
                (activity as? Transfers)?.detailsTransfer()
            },
            setOnLongClickListener@{
                viewModel!!.addToFavorites(it)
                showToastMessage("Added to Favorite")
                viewModel!!.initFavList()
                return@setOnLongClickListener true })
        recycler_view_id.adapter = adapter
    }


    private fun themeBtnText() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            dayNightBtn.text = getString(R.string.day)
        else dayNightBtn.text = getString(R.string.night)
    }

    private fun showToastMessage(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT )
        toast.show()
    }

    interface Transfers {
        fun detailsTransfer()
    }
}




