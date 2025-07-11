package com.lucasbmmn.timetracker.iu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.iu.utils.mnemonicUnderline

@Composable
fun ToolBar(content: @Composable () -> Unit) {
    val theme = LocalTheme.current

    Row(
        modifier = Modifier
            .background(theme.surface)
            .fillMaxWidth()
            .height(50.dp),
    ) {
        content()
    }

    Divider(
        color = theme.background,
        thickness = 1.dp,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToolBarMenu(
    label: String,
    mnemonic: Char? = null,
    content: @Composable () -> Unit
) {
    val theme = LocalTheme.current
    var isExpanded by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(if (isHovered || isExpanded) theme.highlight else theme.surface)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .onPointerEvent(PointerEventType.Press) { if (it.buttons.isPrimaryPressed) isExpanded = !isExpanded }
    ) {
        Text(
            text = mnemonicUnderline(label, mnemonic),
            color = theme.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp, horizontal = 8.dp)
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            content()
        }
    }
}