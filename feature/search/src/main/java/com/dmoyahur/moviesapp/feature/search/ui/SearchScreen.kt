package com.dmoyahur.moviesapp.feature.search.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.core.ui.components.ErrorScreen
import com.dmoyahur.moviesapp.core.ui.components.ImageCoil
import com.dmoyahur.moviesapp.core.ui.components.LoadingIndicator
import com.dmoyahur.moviesapp.core.ui.components.Screen
import com.dmoyahur.moviesapp.feature.search.R
import com.dmoyahur.moviesapp.feature.search.util.header
import kotlin.random.Random

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (MovieBo) -> Unit,
) {
    val state by viewModel.searchUiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    SearchScreen(
        state = state,
        onQueryChange = { viewModel.onQueryChange(it) },
        onActiveChange = { viewModel.onActiveChange(it) },
        onMovieClick = onMovieClick
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    state: SearchUiState,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onMovieClick: (MovieBo) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = state.query,
                onQueryChange = { onQueryChange(it) },
                onSearch = { keyboardController?.hide() },
                active = state.active,
                onActiveChange = { onActiveChange(it) },
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_placeholder)
                    )
                },
                trailingIcon = {
                    if (state.active) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.search_clear),
                            modifier = Modifier.clickable {
                                if (state.query.isEmpty()) {
                                    onActiveChange(false)
                                } else {
                                    onQueryChange("")
                                }
                            }
                        )
                    }
                },
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
                content = {
                    SearchContent(
                        state = state,
                        onMovieClick = onMovieClick
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            )
            if (!state.active) {
                SearchContent(
                    state = state,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun SearchContent(
    state: SearchUiState,
    onMovieClick: (MovieBo) -> Unit,
) {
    val isMoviesEmpty = state.movies.isEmpty()
    val isPreviousSearchesEmpty = state.previousSearches.isEmpty()
    val isQueryEmpty = state.query.isEmpty()
    val isActive = state.active
    val shouldShowPlaceHolderScreen =
        (!isActive && isPreviousSearchesEmpty) || (isActive && isQueryEmpty)
    val shouldShowEmptyScreen = isActive && isMoviesEmpty && !isQueryEmpty

    if (state.error != null) {
        ErrorScreen(error = state.error)
    } else {
        if (shouldShowPlaceHolderScreen) {
            EmptyScreen(icon = Icons.Default.Search, text = R.string.search_content_placeholder)
        } else if (shouldShowEmptyScreen) {
            EmptyScreen(icon = Icons.Default.SearchOff, text = R.string.search_no_results)
        } else {
            SearchList(
                title = stringResource(
                    id = if (isActive) {
                        R.string.search_main_results
                    } else {
                        R.string.search_previous_searches
                    }
                ),
                movies = if (isActive) state.movies else state.previousSearches,
                onMovieClick = onMovieClick,
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 16.dp,
                    bottom = 128.dp
                )
            )
        }

        if (state.loading) {
            LoadingIndicator()
        }
    }
}

@Composable
private fun EmptyScreen(
    icon: ImageVector,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 128.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
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
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        header {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(movies, key = { it.id }) {
            SearchItem(movie = it) { onMovieClick(it) }
        }
    }
}

@Composable
private fun SearchItem(movie: MovieBo, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCoil(
            imageUrl = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
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
private fun PlaceholderScreenPreview() {
    EmptyScreen(icon = Icons.Default.Search, text = R.string.search_content_placeholder)
}

@Preview(showBackground = true)
@Composable
private fun EmptyScreenPreview() {
    EmptyScreen(icon = Icons.Default.SearchOff, text = R.string.search_no_results)
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    Screen {
        SearchScreen(
            state = SearchUiState(
                query = "Movie",
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
                }),
            onQueryChange = {},
            onActiveChange = {},
            onMovieClick = {}
        )
    }
}