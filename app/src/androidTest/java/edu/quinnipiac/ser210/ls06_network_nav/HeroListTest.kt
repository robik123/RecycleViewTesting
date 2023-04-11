package edu.quinnipiac.ser210.ls06_network_nav

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import edu.quinnipiac.ser210.ls06_network_nav.util.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HerosListTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_IN_TEST = 2
    val HERO_IN_TEST = "Wolvarine" //heroList.get(LIST_ITEM_IN_TEST)


    @Before
    fun registerIdlingResource() {
        Log.d("registerIdlingResource","registerIdlingResource executing")
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        EspressoIdlingResource.increment()
        // I am using a kotlin coroutine to simulate a 3 second network request:
        val job = GlobalScope.launch {
            // our network call starts
            delay(3000)
        }
        job.invokeOnCompletion {
            // our network call ended!
            EspressoIdlingResource.decrement()
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }
    @Test
    fun test_backNavigation_toHeroListFragment() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.recyclerview))
            .perform(actionOnItemAtPosition<RecyclerAdapter.MyViewHolder>(LIST_ITEM_IN_TEST, click()))

        // Confirm nav to DetailFragment and display nmae
        onView(withId(R.id.name)).check(matches(withText(HERO_IN_TEST)))

        pressBack()

        // Confirm MovieListFragment in view
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

}