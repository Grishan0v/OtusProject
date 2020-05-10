package com.example.otusproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.otusproject.adapter.MovieFavRecycleAdapter
import com.example.otusproject.databinding.FragmentFavoriteBinding

private const val FAV_ARRAY = "FAV_ARRAY"

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteItems: ArrayList<MovieItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        favoriteItems = arguments?.getParcelableArrayList<MovieItem>(FAV_ARRAY) as ArrayList<MovieItem>
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recycleViewFavorite.layoutAnimation = AnimationUtils.loadLayoutAnimation(LayoutInflater.from(activity).context, R.anim.layout_animation_fall_down)
        binding.recycleViewFavorite.addItemDecoration(SpacingItemDecoration(16))
        binding.recycleViewFavorite.adapter =
            MovieFavRecycleAdapter(LayoutInflater.from(activity), favoriteItems)
    }

    fun newInstance (favoriteItems: ArrayList<MovieItem>) =
        FavoriteFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(FAV_ARRAY, favoriteItems)
            }
        }

}
