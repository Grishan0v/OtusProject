package com.example.otusproject

import com.example.otusproject.data.vo.MovieItem
import com.example.otusproject.ui.screen_home.MovieRecyclerAdapter
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieRecyclerAdapterTest {
    var movieListAdapter: MovieRecyclerAdapter? = null

    @Test
    fun test() {
    assertEquals(4, 2+2)
}
    @Before
    fun setUp() {
        movieListAdapter = MovieRecyclerAdapter(mutableListOf(),
            setOnClickListener@{
            },
            setOnLongClickListener@{
                return@setOnLongClickListener true
            })
    }

    @Test
    fun getEmptyItems() {
        val items = movieListAdapter?.itemCount
        assertEquals(0, items)
    }

    @Test
    fun setItemsAndCheckCount() {
        val items = mutableListOf<MovieItem>()
        repeat(5) {
            items.add(MovieItem(
                true,
                "test",
                it,
                "test",
                "test",
                "test",
                1.0,
                "test",
                "test",
                "test",
                false,
                1.0,
                1,
                false))
        }

        movieListAdapter?.setItems(items)
        assertEquals(items.size, movieListAdapter?.itemCount)
    }

    @Test
    fun clearItemsAndCheckCount() {
        val items = mutableListOf<MovieItem>()
        repeat(5) {
            items.add(MovieItem(
                true,
                "test",
                it,
                "test",
                "test",
                "test",
                1.0,
                "test",
                "test",
                "test",
                false,
                1.0,
                1,
                false))
        }

        movieListAdapter?.setItems(items)
        movieListAdapter?.clearItems()

        assertEquals(0, movieListAdapter?.itemCount)
    }

    @After
    fun tearDown() {
        movieListAdapter = null
    }
}