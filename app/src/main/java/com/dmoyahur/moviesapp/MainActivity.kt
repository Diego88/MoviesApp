package com.dmoyahur.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.data.local.MoviesDatabase
import com.dmoyahur.moviesapp.data.local.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.remote.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.ui.detail.DetailRoute
import com.dmoyahur.moviesapp.ui.detail.DetailViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*MoviesRoute(
                viewModel {
                    MoviesViewModel(
                        repository = MoviesRepository(
                            remoteDataSource = MoviesRemoteDataSource(),
                            localDataSource = MoviesLocalDataSource(db.moviesDao())
                        )
                    )
                }
            )*/
            DetailRoute(
                viewModel = DetailViewModel(
                    repository = MoviesRepository(
                        remoteDataSource = MoviesRemoteDataSource(),
                        localDataSource = MoviesLocalDataSource(db.moviesDao())
                    ),
                    id = 693134
                ),
                onBack = {}
            )
        }
    }
}