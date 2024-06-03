package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.core.ui.components.DefaultTopBar
import com.dmoyahur.moviesapp.core.ui.components.ErrorScreen
import com.dmoyahur.moviesapp.core.ui.components.ImageCoil
import com.dmoyahur.moviesapp.core.ui.components.Screen
import com.dmoyahur.moviesapp.feature.detail.R

@Composable
fun DetailRoute(viewModel: DetailViewModel = hiltViewModel(), onBack: () -> Unit) {

    val state by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    if (state.error != null) {
        ErrorScreen(error = state.error)
    } else {
        DetailScreen(state = state, onBack = onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(state: DetailUiState, onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = state.movie?.title ?: "",
                scrollBehavior = scrollBehavior,
                onBack = onBack
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        state.movie?.let {
            MovieDetail(
                movie = it,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun MovieDetail(movie: MovieBo, modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            ImageCoil(
                imageUrl = movie.backdrop,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            )
            Text(text = movie.overview, modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(16.dp)
            ) {
                MovieDetailProperty(
                    name = stringResource(id = R.string.detail_original_language),
                    value = movie.originalLanguage
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieDetailProperty(
                    name = stringResource(id = R.string.detail_original_title),
                    value = movie.originalTitle
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieDetailProperty(
                    name = stringResource(id = R.string.detail_release_date),
                    value = movie.releaseDate
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieDetailProperty(
                    name = stringResource(id = R.string.detail_popularity),
                    value = movie.popularity.toString()
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieDetailProperty(
                    name = stringResource(id = R.string.detail_vote_average),
                    value = movie.voteAverage.toString()
                )
            }
        }
    }
}

@Composable
private fun MovieDetailProperty(name: String, value: String) {
    Row {
        Text(text = name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    Screen {
        DetailScreen(
            state = DetailUiState(
                movie = MovieBo(
                    id = 1,
                    title = "Movie",
                    overview = "Overview",
                    popularity = 874.167,
                    releaseDate = "2024-02-27",
                    poster = null,
                    backdrop = null,
                    originalTitle = "Movie",
                    originalLanguage = "en",
                    voteAverage = 8.179
                )
            ),
            onBack = {}
        )
    }
}
