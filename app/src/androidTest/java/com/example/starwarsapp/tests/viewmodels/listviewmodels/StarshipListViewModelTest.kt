package com.example.starwarsapp.tests.viewmodels.listviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.listviewmodels.StarshipListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class StarshipListViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var starshipListViewModel: StarshipListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            starshipListViewModel = ViewModelProvider(activity)[StarshipListViewModel::class.java]
        }
    }

    @Test
    fun test_that_get_all_starships_has_items() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(10, starshipListViewModel.starships.value.size)
    }

    @Test
    fun test_that_get_all_starships_has_pagination() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceUntilIdle()
        val job = launch(testDispatcher) {
            starshipListViewModel.starships
                .drop(1)
                .collect {
                    Assert.assertEquals(20, it.size)
                    this.cancel()
                }
        }

        starshipListViewModel.getAllStarships()
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}