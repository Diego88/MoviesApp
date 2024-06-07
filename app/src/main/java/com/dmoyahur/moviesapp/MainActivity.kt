package com.dmoyahur.moviesapp

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = getSystemBarStyle(),
            navigationBarStyle = getSystemBarStyle()
        )

        setContent {
            Screen {
                Navigation()
            }
        }
    }

    private fun getSystemBarStyle(): SystemBarStyle {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> SystemBarStyle.dark(Color.TRANSPARENT)
            Configuration.UI_MODE_NIGHT_NO -> SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
            else -> SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
    }
}