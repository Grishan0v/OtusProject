package com.example.otusproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusproject.databinding.ActivityFavoriteBinding
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteItems: ArrayList<MovieItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val favoriteView = binding.root
        setContentView(favoriteView)
        favoriteItems = intent.getParcelableArrayListExtra<MovieItem>("favoriteItems")
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recycleViewId.layoutManager = LinearLayoutManager(this@FavoriteActivity, RecyclerView.VERTICAL,false)
        binding.recycleViewId.addItemDecoration(SpacingItemDecoration(16))
        binding.recycleViewId.adapter = MovieFavRecycleAdapter(this, favoriteItems)
    }

    override fun onBackPressed() {
        val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
        intent.putExtra("favoriteItems", favoriteItems)
        startActivityForResult(intent, 1)
        finish()
    }
}
