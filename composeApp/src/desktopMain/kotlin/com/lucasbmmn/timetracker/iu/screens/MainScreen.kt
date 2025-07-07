package com.lucasbmmn.timetracker.iu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.components.SideBar
import com.lucasbmmn.timetracker.iu.components.SideBarItem
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.util.Strings

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val theme = LocalTheme.current
    var selectedSideBarItem by remember { mutableStateOf<Int?>(1) }

    Row(modifier.fillMaxSize()) {
        SideBar {
            SideBarItem(
                isSelected = selectedSideBarItem == 0,
                icon = { Icon(Icons.Default.Folder, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.projects")
            ) { selectedSideBarItem = 0 }
            SideBarItem(
                isSelected = selectedSideBarItem == 1,
                icon = { Icon(Icons.AutoMirrored.Rounded.Assignment, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.tasks")
            ) { selectedSideBarItem = 1 }
            SideBarItem(
                isSelected = selectedSideBarItem == 2,
                icon = { Icon(Icons.Rounded.Person, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.clients")
            ) { selectedSideBarItem = 2 }
            SideBarItem(
                isSelected = selectedSideBarItem == 3,
                icon = { Icon(Icons.Rounded.Schedule, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.time_entries")
            ) { selectedSideBarItem = 3 }
            SideBarItem(
                isSelected = selectedSideBarItem == 4,
                icon = { Icon(Icons.Rounded.Analytics, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.reports")
            ) { selectedSideBarItem = 4 }
            SideBarItem(
                isSelected = selectedSideBarItem == 5,
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null, tint = theme.primary, modifier = Modifier.size(32.dp)) },
                text = Strings.get("sidebar.settings")
            ) { selectedSideBarItem = 5 }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.background)
        ) {
        }
    }
}