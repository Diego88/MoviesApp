package com.dmoyahur.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dmoyahur.ui.theme.MoviesAppTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    MoviesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}