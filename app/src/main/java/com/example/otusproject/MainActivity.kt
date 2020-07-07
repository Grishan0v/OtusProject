package com.example.otusproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.otusproject.data.App
import com.example.otusproject.data.vo.MovieItem
import com.example.otusproject.databinding.ActivityMainBinding
import com.example.otusproject.service.AlertReceiver
import com.example.otusproject.ui.screen_details.DetailsFragment
import com.example.otusproject.ui.screen_fav.FavoriteFragment
import com.example.otusproject.ui.screen_home.HomeFragment
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import com.example.otusproject.ui.screen_invite.InviteFragment
import java.util.*

private const val HOME = "HOME"
private const val DETAILS = "DETAILS"
private const val FAVORITE = "FAVORITE"
private const val INVITE = "INVITE"

class MainActivity : AppCompatActivity(), HomeFragment.Transfers, DetailsFragment.Reminder {
    private val viewModel : HomeFragmentViewModel by lazy {
        ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }
    private lateinit var homeFragment: HomeFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var inviteFragment: InviteFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initFragments()
        startFragment(homeFragment)

        val id = intent.getIntExtra("MOVIE_ID", 0)
        if (id > 0) {
            viewModel.showDetailsFromNotification(id)
            startFragment(detailsFragment)
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> startFragment(homeFragment)
                R.id.favorite_menu_item -> startFragment(favoriteFragment)
                R.id.invite_menu_item -> startFragment(inviteFragment)
            }
            true
        }
    }

    private fun initFragments() {
        homeFragment = HomeFragment()
        favoriteFragment = FavoriteFragment()
        detailsFragment = DetailsFragment()
        inviteFragment = InviteFragment()
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_home_screen, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        when (this.supportFragmentManager.findFragmentById(R.id.frame_home_screen)) {
            is HomeFragment -> outState.putString("IS_ACTIVE", HOME)
            is DetailsFragment -> outState.putString("IS_ACTIVE", DETAILS)
            is FavoriteFragment -> outState.putString("IS_ACTIVE", FAVORITE)
            is InviteFragment -> outState.putString("IS_ACTIVE", INVITE)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        when (savedInstanceState.getString("IS_ACTIVE")) {
            HOME -> startFragment(homeFragment)
            DETAILS -> startFragment(detailsFragment)
            FAVORITE -> startFragment(favoriteFragment)
            INVITE -> startFragment(inviteFragment)
        }
    }

    override fun detailsTransfer() {
        startFragment(detailsFragment)
    }

    override fun movieRemind(remindTime: Calendar, movie: MovieItem) {
        Log.d("mTag", movie.id.toString())
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra("TITLE", movie.title)
        intent.putExtra("MOVIE_ID", movie.id)
        val pendingIntent = PendingIntent.getBroadcast(this, movie.id, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, remindTime.timeInMillis, pendingIntent);
        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.compositeDisposable.dispose()
    }
}
