package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.FilmsDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PeopleDetailsViewModel
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
class FilmDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var filmsDetailsViewModel: FilmsDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            filmsDetailsViewModel = ViewModelProvider(activity)[FilmsDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_film_by_id_has_details() = runTest(testDispatcher) {

        val job = launch(testDispatcher) {
            filmsDetailsViewModel.film
                .drop(1)
                .collect {
                    Assert.assertEquals("A New Hope", it.title)
                    this.cancel()
                }
        }

        filmsDetailsViewModel.getFilmById("https://swapi.dev/api/films/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_film_by_id_has_planets() = runTest(testDispatcher) {

        val job = launch(testDispatcher) {
            filmsDetailsViewModel.planets
                .drop(1)
                .collect {
                    Assert.assertEquals("Tatooine", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        filmsDetailsViewModel.getFilmById("https://swapi.dev/api/films/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_film_by_id_has_starships() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            filmsDetailsViewModel.starships
                .drop(1)
                .collect {
                    Assert.assertEquals("CR90 corvette", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        filmsDetailsViewModel.getFilmById("https://swapi.dev/api/films/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_film_by_id_has_vehicles() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            filmsDetailsViewModel.vehicles
                .drop(1)
                .collect {
                    Assert.assertEquals("Sand Crawler", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        filmsDetailsViewModel.getFilmById("https://swapi.dev/api/films/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_film_by_id_has_species() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            filmsDetailsViewModel.species
                .drop(1)
                .collect {
                    Assert.assertEquals("Human", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        filmsDetailsViewModel.getFilmById("https://swapi.dev/api/films/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}