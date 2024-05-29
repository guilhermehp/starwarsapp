package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PlanetsDetailsViewModel
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
class PlanetsDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var planetsDetailsViewModel: PlanetsDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            planetsDetailsViewModel = ViewModelProvider(activity)[PlanetsDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_planet_by_id_has() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            planetsDetailsViewModel.planet
                .drop(1)
                .collect {
                    Assert.assertEquals("Tatooine", it.name)
                    this.cancel()
                }
        }

        planetsDetailsViewModel.getPlanetById("https://swapi.dev/api/planets/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_planet_by_id_has_films() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            planetsDetailsViewModel.films
                .drop(1)
                .collect {
                    Assert.assertEquals("A New Hope", it.first().title)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        planetsDetailsViewModel.getPlanetById("https://swapi.dev/api/planets/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }



    @Test
    fun test_that_planet_by_id_has_people() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            planetsDetailsViewModel.people
                .drop(1)
                .collect {
                    Assert.assertEquals("Luke Skywalker", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        planetsDetailsViewModel.getPlanetById("https://swapi.dev/api/planets/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }
}