package com.dmoyahur.moviesapp.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.core.ui.BackPressedHandler
import com.dmoyahur.moviesapp.core.ui.ErrorScreen
import com.dmoyahur.moviesapp.core.ui.LoadingIndicator
import com.dmoyahur.moviesapp.core.ui.Screen
import com.dmoyahur.moviesapp.feature.search.R
import com.dmoyahur.moviesapp.feature.search.util.header
import java.util.Date
import kotlin.random.Random
import com.dmoyahur.moviesapp.core.ui.R as commonRes

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (MovieBo) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.searchUiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    SearchScreen(
        state = state,
        onQueryChange = { viewModel.onQueryChange(it) },
        onMovieClick = onMovieClick,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    state: SearchUiState,
    onQueryChange: (String) -> Unit,
    onMovieClick: (MovieBo) -> Unit,
    onBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Screen {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) { padding ->
            SearchBar(
                query = state.query,
                onQueryChange = { onQueryChange(it) },
                onSearch = { keyboardController?.hide() },
                active = true,
                onActiveChange = {},
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_placeholder)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.search_clear),
                        modifier = Modifier.clickable { onQueryChange("") }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.error != null) {
                    ErrorScreen(error = state.error)
                } else {
                    val isMoviesEmpty = state.movies.isEmpty()
                    val isPreviousSearchesEmpty = state.previousSearches.isEmpty()
                    val isQueryEmpty = state.query.isEmpty()

                    if ((isPreviousSearchesEmpty && isQueryEmpty) || (isMoviesEmpty && !isQueryEmpty)) {
                        EmptyScreen()
                    } else {
                        SearchList(
                            title = stringResource(
                                id = if (isMoviesEmpty) {
                                    R.string.search_previous_searches
                                } else {
                                    R.string.search_main_results
                                }
                            ),
                            movies = if (isMoviesEmpty) state.previousSearches else state.movies,
                            onMovieClick = onMovieClick,
                            contentPadding = padding,
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 32.dp)
                        )
                    }

                    if (state.loading) {
                        LoadingIndicator()
                    }
                }

                BackPressedHandler {
                    onBack()
                }
            }
        }
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = stringResource(id = commonRes.string.error),
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = stringResource(id = R.string.search_no_results),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun SearchList(
    title: String,
    movies: List<MovieBo>,
    onMovieClick: (MovieBo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        header {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        items(movies, key = { it.id }) {
            SearchItem(movie = it) { onMovieClick(it) }
        }
    }
}

@Composable
private fun SearchItem(movie: MovieBo, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        AsyncImage(
            model = movie.poster ?: commonRes.drawable.poster_placeholder,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = SearchUiState(
            movies = (1..10).map {
                MovieBo(
                    id = it,
                    title = "Movie $it",
                    overview = "Overview $it",
                    popularity = Random.nextDouble(0.0, 10_000.0),
                    releaseDate = Date().toString(),
                    poster = "https://picsum.photos/200/300?id=$it",
                    backdrop = null,
                    originalTitle = "Movie $it",
                    originalLanguage = "en",
                    voteAverage = it / 10.0
                )
            }),
        onQueryChange = {},
        onMovieClick = {},
        onBack = {}
    )
}