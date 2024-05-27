package com.dmoyahur.moviesapp.ui.movies

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.domain.MovieBo
import com.dmoyahur.moviesapp.ui.common.LoadingIndicator
import java.util.Date
import kotlin.random.Random

@Composable
internal fun MoviesRoute(viewModel: MoviesViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    MoviesScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoviesScreen(state: MoviesViewModel.UiState) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->

            if (state.loading) {
                LoadingIndicator()
            }

            MoviesList(
                movies = state.movies,
                contentPadding = padding,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun MoviesList(
    movies: List<MovieBo>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(movies, key = { it.id }) {
            MovieItem(movie = it)
        }
    }
}

@Composable
private fun MovieItem(movie: MovieBo) {
    Column {
        AsyncImage(
            model = movie.poster,
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
fun MoviesScreenPreview() {
    MoviesScreen(
        MoviesViewModel.UiState(
            movies = (1..100).map {
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
            })
    )
}
