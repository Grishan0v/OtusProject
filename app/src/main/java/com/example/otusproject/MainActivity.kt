package com.example.otusproject

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.otusproject.databinding.ActivityMainBinding
private const val FAV_ARRAY = "FAV_ARRAY"
private const val HOME = "HOME"
private const val DETAILS = "DETAILS"
private const val FAVORITE = "FAVORITE"
private const val INVITE = "INVITE"

class MainActivity : AppCompatActivity(), HomeFragment.OnRefresh {
    private lateinit var homeFragment: HomeFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var inviteFragment: InviteFragment
    private lateinit var binding: ActivityMainBinding
    private var favoriteItems: ArrayList<MovieItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initFragments()
        startFragment(homeFragment)


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> startFragment(homeFragment)
                R.id.favorite_menu_item -> startFragment(favoriteFragment.newInstance(favoriteItems))
                R.id.invite_menu_item -> startFragment(inviteFragment)
            }
            true
        }
    }

    private fun initFragments() {
        homeFragment = HomeFragment().newInstance(favoriteItems)
        favoriteFragment = FavoriteFragment().newInstance(favoriteItems)
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

    override fun favoritesRefresh(favorites: ArrayList<MovieItem>) {
        favoriteItems = favorites
    }

    override fun startDetailFragment(item: MovieItem) {
        startFragment(DetailsFragment().newInstance(favoriteItems, item))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(FAV_ARRAY, favoriteItems)
        when (this.supportFragmentManager.findFragmentById(R.id.frame_home_screen)) {
            is HomeFragment -> outState.putString("IS_ACTIVE", HOME)
            is DetailsFragment -> outState.putString("IS_ACTIVE", DETAILS)
            is FavoriteFragment -> outState.putString("IS_ACTIVE", FAVORITE)
            is InviteFragment -> outState.putString("IS_ACTIVE", INVITE)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        favoriteItems = savedInstanceState
            .getParcelableArrayList<MovieItem>(FAV_ARRAY) as ArrayList<MovieItem>

        when (savedInstanceState.getString("IS_ACTIVE")) {
            HOME -> startFragment(homeFragment)
            DETAILS -> startFragment(detailsFragment)
            FAVORITE -> startFragment(favoriteFragment.newInstance(favoriteItems))
            INVITE -> startFragment(inviteFragment)

        }
    }
}
