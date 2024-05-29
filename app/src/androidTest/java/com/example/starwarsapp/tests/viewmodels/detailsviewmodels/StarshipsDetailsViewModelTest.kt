package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.SpeciesDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.StarshipsDetailsViewModel
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
class StarshipsDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var starshipsDetailsViewModel: StarshipsDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            starshipsDetailsViewModel = ViewModelProvider(activity)[StarshipsDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_starship_by_id_has_details() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            starshipsDetailsViewModel.starship
                .drop(1)
                .collect {
                    Assert.assertEquals("CR90 corvette", it.name)
                    this.cancel()
                }
        }
        starshipsDetailsViewModel.getSpeciesById("https://swapi.dev/api/starships/2/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_starship_by_id_has_films() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            starshipsDetailsViewModel.films
                .drop(1)
                .collect {
                    Assert.assertEquals("A New Hope", it.first().title)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }
        starshipsDetailsViewModel.getSpeciesById("https://swapi.dev/api/starships/2/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}