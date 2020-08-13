package com.example.otusproject

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var favoriteItems: ArrayList<MovieItem> = ArrayList<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()

        if(intent.getParcelableArrayListExtra<MovieItem>("favoriteItems")!=null) {
            favoriteItems = intent.getParcelableArrayListExtra<MovieItem>("favoriteItems")
        }

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.dayNightBtn.text = getString(R.string.day)
        } else {
            binding.dayNightBtn.text = getString(R.string.night)}

        binding.inviteBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.inviteMessage))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }

        binding.favoriteBtn.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            intent.putExtra("favoriteItems", favoriteItems)
            startActivity(intent)
            finish()
        }

        binding.dayNightBtn.setOnClickListener{
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                binding.dayNightBtn.text = getString(R.string.night)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                binding.dayNightBtn.text = getString(R.string.day)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initRecyclerView() {
        binding.reciclerViewId.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL,false)
        binding.reciclerViewId.addItemDecoration(SpacingItemDecoration(30))
        val items = createMovieSet()

        binding.reciclerViewId.adapter = MovieRecyclerAdapter(this, items, setOnClickListener@{
                movieItem ->
            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
            intent.putExtra("PARCEL", movieItem)
            intent.putExtra("favoriteItems", favoriteItems)
            startActivity(intent)
            finish()
        }, setOnLongClickListener@{ movieItem ->
            if (!favoriteItems.contains(movieItem)) {
                favoriteItems.add(movieItem)
                val toast = Toast.makeText(
                    applicationContext,
                    getString(R.string.toastAddedToFav),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Already in Favorite",
                    Toast.LENGTH_SHORT)
                toast.show()
            }

            return@setOnLongClickListener true
        })
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        if(intent.getParcelableArrayListExtra<MovieItem>("favoriteItems")!= null) {
            favoriteItems = intent.getParcelableArrayListExtra<MovieItem>("favoriteItems")
        }
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(getString(R.string.alert_exit))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _: DialogInterface, _: Int ->
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { d: DialogInterface, _: Int ->
                d.cancel()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun createMovieSet():  ArrayList<MovieItem> {
        var movieList = ArrayList<MovieItem>()

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

}
