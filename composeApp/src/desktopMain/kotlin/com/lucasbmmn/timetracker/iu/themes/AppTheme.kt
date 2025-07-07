package com.lucasbmmn.timetracker.iu.themes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun AppTheme(
    initialTheme: Theme = DarkTheme,
    content: @Composable () -> Unit
) {
    val controller = remember { ThemeController(initialTheme) }

    CompositionLocalProvider(
        LocalTheme provides controller.theme,
        LocalThemeController provides controller
    ) {
        content()
    }
}