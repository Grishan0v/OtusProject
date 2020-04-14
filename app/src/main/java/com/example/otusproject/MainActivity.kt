package com.example.otusproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.otusproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.detailsBtn1.setOnClickListener{
            binding.movieName1.setTextColor(Color.RED)
            val intent = Intent(this, Movie1Activity::class.java)
            startActivity(intent)
        }

        binding.detailsBtn2.setOnClickListener{
            binding.movieName2.setTextColor(Color.RED)
            val intent = Intent(this, Movie2Activity::class.java)
            startActivity(intent)
        }

         binding.detailsBtn3.setOnClickListener{
            binding.movieName3.setTextColor(Color.RED)
            val intent = Intent(this, Movie3Activity::class.java)
            startActivity(intent)
        }

        binding.inviteBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.inviteMessage))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }

        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                binding.switchMode.text = "Night"
                // Change the app background color

            } else {
                // The switch is disabled
                binding.switchMode.text = "Day"
                // Set the app background color to light gray

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        val name = intent.getStringExtra("movieName")
        if (name != null) {
            when (name) {
                "Blade Runner 2049" -> binding.movieName1.setTextColor(Color.RED)
                "Joker" -> binding.movieName2.setTextColor(Color.RED)
                "Knives Out" -> binding.movieName3.setTextColor(Color.RED)
            }

            when (intent.getBooleanExtra("checkBox", false)) {
                true -> Log.d("MyTag", "$name is your favorite")
                false -> Log.d("MyTag", "$name is not your favorite")
            }
            Log.d("MyTag", "$name notes: ${intent.getStringExtra("notes")}")

        }
    }
}
