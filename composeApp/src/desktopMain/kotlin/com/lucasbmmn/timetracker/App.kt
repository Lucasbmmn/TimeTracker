package com.lucasbmmn.timetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.components.DropDownMenuItem
import com.lucasbmmn.timetracker.iu.components.ToolBarMenu
import com.lucasbmmn.timetracker.iu.components.ToolBar
import com.lucasbmmn.timetracker.iu.screens.MainScreen
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.util.Strings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val theme = LocalTheme.current

    Column(
        modifier = Modifier
            .background(color = theme.background)
    ) {
        ToolBar {
            ToolBarMenu(
                Strings.get("menu.file"),
                Strings.get("menu.file.mnemonic")[0],
                content = {
                DropDownMenuItem(
                    Strings.get("menu.file.new"),
                    Strings.get("menu.file.new.mnemonic")[0],
                ) { println("menu.file.new") }
                DropDownMenuItem(
                    Strings.get("menu.file.open"),
                    Strings.get("menu.file.open.mnemonic")[0],
                ) { println("menu.file.open") }
                DropDownMenuItem(
                    Strings.get("menu.file.save"),
                    Strings.get("menu.file.save.mnemonic")[0],
                ) { println("menu.file.save") }
                DropDownMenuItem(
                    Strings.get("menu.file.saveAs"),
                    Strings.get("menu.file.saveAs.mnemonic")[0],
                ) { println("menu.file.saveAs") }
                Divider(
                    modifier = Modifier
                        .padding(0.dp, 2.5.dp, 0.dp, 2.5.dp),
                    color = theme.outline,
                    thickness = 1.dp,
                )
                DropDownMenuItem(
                    Strings.get("menu.file.quit"),
                    Strings.get("menu.file.quit.mnemonic")[0],
                ) { println("menu.file.quit") }
            })
            ToolBarMenu(
                Strings.get("menu.edit"),
                Strings.get("menu.edit.mnemonic")[0],
                content = {
                DropDownMenuItem(
                    Strings.get("menu.edit.copy"),
                    Strings.get("menu.edit.copy.mnemonic")[0],
                ) { println("menu.edit.copy") }
                DropDownMenuItem(
                    Strings.get("menu.edit.cut"),
                    Strings.get("menu.edit.cut.mnemonic")[0],
                ) { println("menu.edit.cut") }
                DropDownMenuItem(
                    Strings.get("menu.edit.paste"),
                    Strings.get("menu.edit.paste.mnemonic")[0],
                ) { println("menu.edit.paste") }
            })
            ToolBarMenu(
                Strings.get("menu.help"),
                Strings.get("menu.help.mnemonic")[0],
                content = {
                DropDownMenuItem(
                    Strings.get("menu.help.about"),
                    Strings.get("menu.help.about.mnemonic")[0],
                ) { println("menu.help.about") }
            })
        }

        MainScreen()
    }
}