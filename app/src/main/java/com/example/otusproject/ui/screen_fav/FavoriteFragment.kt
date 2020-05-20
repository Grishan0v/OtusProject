package com.example.otusproject.ui.screen_fav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.otusproject.R
import com.example.otusproject.data.vo.Movie
import com.example.otusproject.SpacingItemDecoration
import com.example.otusproject.databinding.FragmentFavoriteBinding
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {
    private var viewModel: HomeFragmentViewModel? = null
    private var favoriteMovies = mutableListOf<Movie>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        viewModel!!.favorites.observe(this.viewLifecycleOwner, Observer {fav ->
            favoriteMovies.clear()
            favoriteMovies.addAll(fav)})
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycle_view_favorite.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            LayoutInflater.from(activity).context,
            R.anim.layout_animation_fall_down
        )

        recycle_view_favorite.addItemDecoration(SpacingItemDecoration(16))

        recycle_view_favorite.adapter =
            MovieFavRecycleAdapter(
                LayoutInflater.from(activity),
                favoriteMovies) {
                viewModel!!.removeItemFromFavorites(it)
            }
    }
}
