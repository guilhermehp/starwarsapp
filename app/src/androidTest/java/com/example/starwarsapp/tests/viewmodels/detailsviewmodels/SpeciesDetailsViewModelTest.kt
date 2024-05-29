package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.FilmsDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.SpeciesDetailsViewModel
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
class SpeciesDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var speciesDetailsViewModel: SpeciesDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            speciesDetailsViewModel = ViewModelProvider(activity)[SpeciesDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_species_by_id_das_details() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            speciesDetailsViewModel.species
                .drop(1)
                .collect {
                    Assert.assertEquals("Human", it.name)
                    this.cancel()
                }
        }

        speciesDetailsViewModel.getSpeciesById("https://swapi.dev/api/species/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_species_by_has_home_world() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            speciesDetailsViewModel.planet
                .drop(1)
                .collect {
                    Assert.assertEquals("Coruscant", it.name)
                    this.cancel()
                }
        }

        speciesDetailsViewModel.getSpeciesById("https://swapi.dev/api/species/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_species_by_id_has_people() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            speciesDetailsViewModel.people
                .drop(1)
                .collect {
                    Assert.assertEquals("Dormé", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        speciesDetailsViewModel.getSpeciesById("https://swapi.dev/api/species/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_species_by_id_has_films() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            speciesDetailsViewModel.films
                .drop(1)
                .collect {
                    Assert.assertEquals("A New Hope", it.first().title)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }
        speciesDetailsViewModel.getSpeciesById("https://swapi.dev/api/species/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}