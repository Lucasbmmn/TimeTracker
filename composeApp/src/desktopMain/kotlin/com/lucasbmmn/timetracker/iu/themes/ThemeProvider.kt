package com.lucasbmmn.timetracker.iu.themes

import androidx.compose.runtime.compositionLocalOf

val LocalTheme = compositionLocalOf<Theme> {
    error("No Theme provided. Did you forget to wrap your app in AppTheme?")
}