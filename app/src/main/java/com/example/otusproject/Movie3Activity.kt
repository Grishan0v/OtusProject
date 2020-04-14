package com.example.otusproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.otusproject.databinding.MovieDetails3LayoutBinding

class Movie3Activity : AppCompatActivity() {
    private lateinit var binding: MovieDetails3LayoutBinding
    private var noteText = ""
    private lateinit var note: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetails3LayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        val intentM1 = Intent(this@Movie3Activity, MainActivity::class.java)
        intentM1.putExtra("checkBox", binding.checkBox.isChecked)
        intentM1.putExtra("movieName", "Knives Out")
        intentM1.putExtra("notes", noteText)
        startActivityForResult(intentM1, 1)
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
