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
class PeopleDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_people_has_details(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("height"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("height").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("172"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("172").assertExists()
    }

    @Test
    fun test_that_people_has_home_world(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("planet"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planet").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("tatooine"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").assertExists()
    }

    @Test
    fun test_that_people_has_films(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performScrollTo().assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("films"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").assertExists()
    }

    @Test
    fun test_that_people_has_vehicles(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("vehicles"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("vehicles").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("snowspeeder"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("snowspeeder").assertExists()
    }

    @Test
    fun test_that_people_has_starships(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("luke skywalker").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("starships"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("starships").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.PEOPLE_LIST).performScrollToNode(hasText("x-wing"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("x-wing").assertExists()
    }

}