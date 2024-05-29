package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.FilmsDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PeopleDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.listviewmodels.FilmListViewModel
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
class PeopleDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var peopleDetailsViewModel: PeopleDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            peopleDetailsViewModel = ViewModelProvider(activity)[PeopleDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_people_by_id_has_details() = runTest(testDispatcher) {

        val job = launch(testDispatcher) {
            peopleDetailsViewModel.people
                .drop(1)
                .collect {
                    Assert.assertEquals("Luke Skywalker", it.name)
                    this.cancel()
                }
        }

        peopleDetailsViewModel.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_people_by_id_has_home_world() = runTest(testDispatcher) {

        val job = launch(testDispatcher) {
            peopleDetailsViewModel.planet
                .drop(1)
                .collect {
                    Assert.assertEquals("Tatooine", it.name)
                    this.cancel()
                }
        }

        peopleDetailsViewModel.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_people_by_id_has_starships() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModel.starships
                .drop(1)
                .collect {
                    Assert.assertEquals("X-wing", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        peopleDetailsViewModel.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_film_by_id_has_vehicles() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModel.vehicles
                .drop(1)
                .collect {
                    Assert.assertEquals("Snowspeeder", it.first().name)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        peopleDetailsViewModel.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}