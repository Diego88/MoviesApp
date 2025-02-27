package com.dmoyahur.moviesapp.feature.movies.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmoyahur.moviesapp.common.ui.components.DefaultTopBar
import com.dmoyahur.moviesapp.common.ui.components.ErrorScreen
import com.dmoyahur.moviesapp.common.ui.components.ImageCoil
import com.dmoyahur.moviesapp.common.ui.components.LoadingIndicator
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.common.util.Constants.POSTER_ASPECT_RATIO
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.feature.movies.R
import com.dmoyahur.moviesapp.model.MovieBo
import kotlin.random.Random

@Composable
fun MoviesRoute(viewModel: MoviesViewModel = hiltViewModel(), onMovieClick: (MovieBo) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    MoviesScreen(state, onMovieClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@VisibleForTesting
fun MoviesScreen(state: MoviesUiState, onMovieClick: (MovieBo) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(id = R.string.movies_popular),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        when (state) {
            is MoviesUiState.Success -> MoviesList(
                movies = state.movies,
                contentPadding = padding,
                onMovieClick = onMovieClick,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 100.dp)
            )
            is MoviesUiState.Error -> ErrorScreen(state.exception)
            is MoviesUiState.Loading -> LoadingIndicator()
        }
    }
}

@Composable
private fun MoviesList(
    movies: List<MovieBo>,
    contentPadding: PaddingValues,
    onMovieClick: (MovieBo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.testTag(TestConstants.Movies.MOVIES_LIST_TAG)
    ) {
        items(movies, key = { it.id }) {
            MovieItem(movie = it) { onMovieClick(it) }
        }
    }
}

@Composable
private fun MovieItem(movie: MovieBo, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCoil(
            imageUrl = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(POSTER_ASPECT_RATIO)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenPreview() {
    Screen {
        MoviesScreen(
            state = MoviesUiState.Success(
                movies = (1..10).map {
                    MovieBo(
                        id = it,
                        title = "Movie $it",
                        overview = "Overview $it",
                        popularity = Random.nextDouble(0.0, 10_000.0),
                        releaseDate = "",
                        poster = null,
                        backdrop = null,
                        originalTitle = "Movie $it",
                        originalLanguage = "en",
                        voteAverage = it / 10.0
                    )
                }
            ),
            onMovieClick = {}
        )
    }
}