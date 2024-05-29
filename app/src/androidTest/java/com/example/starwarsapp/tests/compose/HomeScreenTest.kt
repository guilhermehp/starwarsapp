package com.example.starwarsapp.tests.compose

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.domain.utils.TestingTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_people_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").assertExists()
    }

    @Test
    fun test_that_films_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").assertExists()
    }

    @Test
    fun test_that_planets_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planets").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").performScrollTo().assertExists()
    }

    @Test
    fun test_that_species_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").assertExists()
    }

    @Test
    fun test_that_starships_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("starships").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("cr90 corvette").assertExists()
    }

    @Test
    fun test_that_vehicles_list_exists(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("vehicles").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("sand crawler").assertExists()
    }

}