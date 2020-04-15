package com.example.otusproject

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.otusproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.dayNightBtn.text = getString(R.string.day)
        } else {
            binding.dayNightBtn.text = getString(R.string.night)}

        binding.detailsBtn1.setOnClickListener{
            binding.movieName1.setTextColor(Color.RED)
            val intent = Intent(this, Movie1Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.detailsBtn2.setOnClickListener{
            binding.movieName2.setTextColor(Color.RED)
            val intent = Intent(this, Movie2Activity::class.java)
            startActivity(intent)
            finish()
        }

         binding.detailsBtn3.setOnClickListener{
            binding.movieName3.setTextColor(Color.RED)
            val intent = Intent(this, Movie3Activity::class.java)
            startActivity(intent)
             finish()
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
}
