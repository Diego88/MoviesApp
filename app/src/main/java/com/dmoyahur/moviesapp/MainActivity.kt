package com.dmoyahur.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dmoyahur.moviesapp.data.remote.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.ui.movies.MoviesScreen
import com.dmoyahur.moviesapp.ui.movies.MoviesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesScreen(
                viewModel { MoviesViewModel(MoviesRepository(MoviesRemoteDataSource())) }
            )
        }
    }
}