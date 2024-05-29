package com.example.starwarsapp.tests.compose

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.domain.utils.TestingTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_app_basic_navigation_flow_works(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FAVORITES_BUTTON).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("tatooine"))
        composeTestRule.onNodeWithText("tatooine").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PLANETS_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.HOME_BUTTON).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("STAR WARS").assertExists()
    }
}