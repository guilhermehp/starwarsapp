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
class SpeciesDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_that_species_has_details(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("classification"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("classification").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("mammal"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("mammal").assertExists()
    }

    @Test
    fun test_that_species_has_home_world(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("home world"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("home world").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("coruscant"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("coruscant").assertExists()
    }

    @Test
    fun test_that_species_has_characters(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("characters"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("characters").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("dormé"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("dormé").assertExists()
    }

    @Test
    fun test_that_species_has_films(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("species").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("human").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("films"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("films").assertExists()
        composeTestRule.onNodeWithTag(TestingTags.SPECIES_LIST).performScrollToNode(hasText("a new hope"))
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("a new hope").assertExists()
    }

}