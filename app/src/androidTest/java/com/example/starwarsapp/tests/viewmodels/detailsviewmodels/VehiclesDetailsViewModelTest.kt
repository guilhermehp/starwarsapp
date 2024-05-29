package com.example.starwarsapp.tests.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.StarshipsDetailsViewModel
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.VehiclesDetailsViewModel
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
class VehiclesDetailsViewModelTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var vehiclesDetailsViewModel: VehiclesDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        hiltRule.inject()
        activityScenarioRule.scenario.onActivity { activity ->
            vehiclesDetailsViewModel = ViewModelProvider(activity)[VehiclesDetailsViewModel::class.java]
        }
    }


    @Test
    fun test_that_vehicle_by_id_has_details() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            vehiclesDetailsViewModel.vehicle
                .drop(1)
                .collect {
                    Assert.assertEquals("Sand Crawler", it.name)
                    this.cancel()
                }
        }

        vehiclesDetailsViewModel.getVehicleById("https://swapi.dev/api/vehicles/4/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @Test
    fun test_that_vehicles_by_id_has_films() = runTest(testDispatcher) {
        val job = launch(testDispatcher) {
            vehiclesDetailsViewModel.films
                .drop(1)
                .collect {
                    Assert.assertEquals("A New Hope", it.first().title)
                    Assert.assertEquals(2, it.size)
                    this.cancel()
                }
        }

        vehiclesDetailsViewModel.getVehicleById("https://swapi.dev/api/vehicles/4/")
        testDispatcher.scheduler.advanceUntilIdle()
        job.join()
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

}