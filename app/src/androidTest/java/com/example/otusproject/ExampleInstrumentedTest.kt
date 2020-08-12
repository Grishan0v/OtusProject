package com.example.otusproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.otusproject.ui.screen_home.MovieRecyclerAdapter
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)
    private var mIdlingResource: IdlingResource? = null

    @Test
    fun openDetails() {
        onView(withId(R.id.recycler_view_id)).perform(
            actionOnItemAtPosition<MovieRecyclerAdapter.MovieViewHolder>(7, click())
        )
        onView(withId(R.id.title)).check(matches(isDisplayed()))
    }

    @Test
    fun day_night_switch() {
        onView(withId(R.id.dayNightBtn)).check(matches(withText(R.string.night)))

        onView(withId(R.id.dayNightBtn))
            .perform(click())
            .check(matches(withText(R.string.day)))
    }

    @Test
    fun add_movie_to_favorite() {
        onView(withId(R.id.recycler_view_id)).perform(
            actionOnItemAtPosition<MovieRecyclerAdapter.MovieViewHolder>(1, longClick())
        )
        onView(withId(R.id.favorite_menu_item))
            .perform(click())

        onView(withId(R.id.recycle_view_favorite)).check(matches(hasChildCount(1)))


    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.otusproject", appContext.packageName)
    }
    @Before
    fun registerIdlingResource() {
        activityScenarioRule.scenario.onActivity { activity ->
            mIdlingResource = activity.idlingResource
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }
}
