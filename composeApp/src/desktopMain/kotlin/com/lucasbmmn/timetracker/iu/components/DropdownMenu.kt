package com.lucasbmmn.timetracker.iu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.iu.utils.mnemonicUnderline


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = LocalTheme.current

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(theme.surface)
            .border(0.5.dp, theme.outline, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp)
    ) {
        content()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DropDownMenuItem(
    label: String,
    mnemonic: Char? = null,
    onClick: () -> Unit,
) {
    val theme = LocalTheme.current
    var isHovered by remember { mutableStateOf(false) }

    DropdownMenuItem(
        onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isHovered) theme.highlightStrong else theme.surface)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .height(25.dp)
    ) {
        Text(
            text = mnemonicUnderline(label, mnemonic),
            color = theme.onSurface,
        )
    }
}