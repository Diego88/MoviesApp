package com.dmoyahur.moviesapp.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.dmoyahur.moviesapp.MainActivity
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.testShared.utils.assertTextIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.assertTextIsSelected
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithText
import com.dmoyahur.moviesapp.utils.InstrumentedTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import com.dmoyahur.moviesapp.feature.movies.R as MoviesR
import com.dmoyahur.moviesapp.feature.search.R as SearchR

@HiltAndroidTest
class NavigationTest : InstrumentedTest() {

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigationBar_moviesScreenIsDisplayed(): Unit = with(composeTestRule) {
        assertTextIsSelected(R.string.movies)
        assertTextIsDisplayed(MoviesR.string.movies_popular)
    }

    @Test
    fun navigationBar_clickOnSearch_searchScreenIsDisplayed(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        assertTextIsSelected(R.string.search)
        assertTextIsDisplayed(SearchR.string.search_placeholder)
    }

    @Test
    fun navigationBar_clickOnMovies_moviesScreenIsDisplayed(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        clickOnNodeWithText(R.string.movies)

        assertTextIsSelected(R.string.movies)
        assertTextIsDisplayed(MoviesR.string.movies_popular)
    }

    @Test
    fun searchScreen_back_returnsMoviesScreen(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        Espresso.pressBack()

        assertTextIsSelected(R.string.movies)
    }

    @Test(expected = NoActivityResumedException::class)
    fun moviesScreen_back_quitsApp(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        clickOnNodeWithText(R.string.movies)

        Espresso.pressBack()
    }
}