package com.example.otusproject

import com.example.otusproject.ui.screen_fav.MovieFavRecycleAdapter
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieFavRecyclerAdapterTest {
    private var movieListAdapter: MovieFavRecycleAdapter? = null

    @Before
    fun setUp() {
        movieListAdapter = MovieFavRecycleAdapter(mutableListOf()) {
        }
    }

    @Test
    fun getEmptyItems() {
        val items = movieListAdapter?.itemCount
        assertEquals(0, items)
    }


    @After
    fun tearDown() {
        movieListAdapter = null
    }
}