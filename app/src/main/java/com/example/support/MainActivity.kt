package com.example.support

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.support.core.navigation.MainNavigation
import com.example.support.core.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent(
) {
    AppTheme(darkTheme = false) {
        MainNavigation()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme(
        darkTheme = false
    ) {
        MainContent()
    }
}