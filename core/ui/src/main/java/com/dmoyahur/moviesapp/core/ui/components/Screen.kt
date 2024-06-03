package com.dmoyahur.moviesapp.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dmoyahur.moviesapp.core.ui.theme.MoviesAppTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    MoviesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}