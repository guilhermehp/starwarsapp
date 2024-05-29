package com.example.starwarsapp.tests.compose

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.domain.utils.TestingTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PlanetsDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_planet_has_details(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planets").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("terrain"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("terrain").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("desert"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("desert").assertExists()
    }

    @Test
    fun test_that_planet_has_residents(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planets").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("residents"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("residents").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("luke skywalker"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").assertExists()
    }


    @Test
    fun test_that_planet_has_films(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planets").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("films"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").assertExists()
    }

}