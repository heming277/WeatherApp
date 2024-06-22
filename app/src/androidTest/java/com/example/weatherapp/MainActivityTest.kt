package com.example.weatherapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCurrentWeatherDisplay() {
        // Check if the RecyclerView for the weather is displayed
        onView(withId(R.id.city_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchViewDisplay() {
        // Check if the search view is displayed
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testEnterCityName() {
        // Enter a city name in the search view and press the done action
        onView(withId(R.id.search_view))
            .perform(typeText("Toronto"), pressImeActionButton())

        // Wait for the RecyclerView to be updated (up to 5 seconds)
        val maxRetries = 10
        val retryIntervalMs = 500L
        var retries = 0
        var success = false

        while (retries < maxRetries && !success) {
            try {
                // Check if the RecyclerView is updated with the new weather data
                onView(withId(R.id.city_list)).check(matches(hasDescendant(withSubstring("Toronto"))))
                success = true
            } catch (e: AssertionError) {
                retries++
                Thread.sleep(retryIntervalMs)
            }
        }

        if (!success) {
            throw AssertionError("RecyclerView was not updated with Toronto data after ${maxRetries * retryIntervalMs / 1000} seconds")
        }
    }
}