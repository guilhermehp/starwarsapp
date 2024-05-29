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
class VehiclesDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_vehicle_has_details(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("vehicles").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("sand crawler").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.VEHICLES_LIST).performScrollToNode(hasText("model"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("model").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.VEHICLES_LIST).performScrollToNode(hasText("digger crawler"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("digger crawler").assertExists()
    }

    @Test
    fun test_that_vehicle_has_movies(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("vehicles").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("sand crawler").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.VEHICLES_LIST).performScrollToNode(hasText("films"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.VEHICLES_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").assertExists()
    }

}