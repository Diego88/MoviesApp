package com.dmoyahur.moviesapp.feature.movies

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dmoyahur.moviesapp.MainActivity
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.testShared.utils.assertTagIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.assertTextIsDisplayed
import com.dmoyahur.moviesapp.utils.InstrumentedTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import com.dmoyahur.moviesapp.feature.movies.R as MoviesR

@HiltAndroidTest
class MoviesScreenTest : InstrumentedTest() {

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appStarts_moviesScreenIsDisplayed(): Unit = with(composeTestRule) {
        assertTextIsDisplayed(R.string.movies)
        assertTagIsDisplayed(TestConstants.Movies.MOVIES_LIST_TAG)
    }

    @Test
    fun moviesScreen_topBarIsDisplayed(): Unit = with(composeTestRule) {
        assertTextIsDisplayed(MoviesR.string.movies_popular)
    }

    @Test
    fun moviesScreen_IsScrollable(): Unit = with(composeTestRule) {
        val movieToScroll by lazy { hasText("Kung Fu Panda 4") }

        scrollToMovie(movieToScroll)
    }

    @Test
    fun moviesScreen_clickOnMovie_detailsScreenIsDisplayed(): Unit = with(composeTestRule) {
        clickOnMovie(0)

        assertTagIsDisplayed(TestConstants.Common.TOP_BAR_TITLE_TAG)
    }
}