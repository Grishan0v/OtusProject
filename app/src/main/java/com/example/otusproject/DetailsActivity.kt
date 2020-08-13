package com.example.otusproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bumptech.glide.Glide
import com.example.otusproject.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var noteText = ""
    private lateinit var note: Editable
    private lateinit var favoriteItems: ArrayList<MovieItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        favoriteItems = intent.getParcelableArrayListExtra("favoriteItems")

        val movieItem = intent.getParcelableExtra<MovieItem>("PARCEL")
        val img = binding.moviePoster
        val title = binding.title
        val movieDate = binding.movieReleaseDate
        val starringActors = binding.starringActors
        val director = binding.director
        val rating = binding.rating
        val description = binding.description


        Glide.with(view.context)
            .load(movieItem?.moviePoster)
            .into(img)

        title.text = movieItem?.title
        movieDate.text = movieItem?.year
        starringActors.text = movieItem?.starring
        director.text = movieItem?.director
        rating.text = movieItem?.rating
        description.text = movieItem?.description

        binding.notes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    note = s
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                noteText = s.toString()
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@DetailsActivity, MainActivity::class.java)
        intent.putExtra("notes", noteText)
        intent.putExtra("favoriteItems", favoriteItems)
        startActivityForResult(intent, 1)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("NOTES_TEXT", noteText)
        outState.putBoolean("isBox", binding.checkBox.isChecked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.notes.setText(savedInstanceState["NOTES_TEXT"].toString())
        binding.checkBox.isChecked = savedInstanceState["isBox"] as Boolean
    }
}
