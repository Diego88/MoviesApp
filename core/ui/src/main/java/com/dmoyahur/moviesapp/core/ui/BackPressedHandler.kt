package com.dmoyahur.moviesapp.core.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun BackPressedHandler(
    lifecycleOwner: OnBackPressedDispatcherOwner? = LocalOnBackPressedDispatcherOwner.current,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backPressedDispatcher: OnBackPressedDispatcher? = lifecycleOwner?.onBackPressedDispatcher

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    LaunchedEffect(key1 = lifecycleOwner) {
        backPressedDispatcher?.addCallback(backCallback)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            backCallback.remove()
        }
    }
}