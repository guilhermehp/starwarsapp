package com.example.starwarsapp.tests.viewmodels.listviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.tests.viewmodels.detailsviewmodels.PeopleDetailsViewModelTest
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PeopleDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.listviewmodels.FavoritesViewModel
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

    private lateinit var peopleDetailsViewModelTest: PeopleDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            peopleDetailsViewModelTest = ViewModelProvider(activity)[PeopleDetailsViewModel::class.java]
        }
    }

    @Test
    fun test_that_people_by_id_has_items() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModelTest.people
                .drop(1)
                .collect{
                    Assert.assertEquals("Luke Skywalker", it.name)
                    this@launch.cancel()
                }
        }

        peopleDetailsViewModelTest.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_people_by_id_has_home_world() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModelTest.planet
                .drop(1)
                .collect{
                    Assert.assertEquals("Tatooine", it.name)
                    this@launch.cancel()
                }
        }

        peopleDetailsViewModelTest.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_get_people_by_id_vehicles() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModelTest.vehicles
                .drop(1)
                .collect{
                    Assert.assertEquals(2, it.size)
                    this@launch.cancel()
                }
        }

        peopleDetailsViewModelTest.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_get_people_by_id_starships() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            peopleDetailsViewModelTest.starships
                .drop(1)
                .collect{
                    Assert.assertEquals(2, it.size)
                    this@launch.cancel()
                }
        }

        peopleDetailsViewModelTest.getPeopleById("https://swapi.dev/api/people/1/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}