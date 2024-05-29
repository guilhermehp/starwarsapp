package com.example.starwarsapp.tests.compose

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.example.starwarsapp.MainActivity
import com.example.starwarsapp.domain.utils.TestingTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FilmsDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_film_has_details(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("director")).assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("george lucas")).assertExists()
    }

    @Test
    fun test_that_film_has_characters(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("characters")).assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("luke skywalker")).assertExists()
    }

    @Test
    fun test_that_film_has_planets(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("planets"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("planets").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("tatooine"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("tatooine").assertExists()
    }

    @Test
    fun test_that_film_has_starships(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("starships"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("starships").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("cr90 corvette"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("cr90 corvette").assertExists()
    }

    @Test
    fun test_that_film_has_vehicles(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("vehicles"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("vehicles").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("sand crawler"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("sand crawler").assertExists()
    }

    @Test
    fun test_that_film_has_species(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("species"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.FILMS_LIST).performScrollToNode(hasText("human"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").assertExists()
    }
}