package com.lucasbmmn.timetracker

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lucasbmmn.timetracker.iu.themes.DarkTheme
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.iu.themes.LocalThemeController
import com.lucasbmmn.timetracker.iu.themes.ThemeController

fun main() = application {
    val controller = remember { ThemeController(DarkTheme) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "TimeTracker",
    ) {
        CompositionLocalProvider(
            LocalTheme provides controller.theme,
            LocalThemeController provides controller
        ) {
            App()
        }
    }
}