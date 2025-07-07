package com.lucasbmmn.timetracker.iu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import kotlin.text.forEach

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
fun Menu(
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
            .onPointerEvent(PointerEventType.Press) { isExpanded = !isExpanded }
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
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(theme.surface)
                .border(0.5.dp, theme.outline, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Item(
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


fun mnemonicUnderline(label: String, mnemonic: Char?): AnnotatedString  {
    return buildAnnotatedString {
        var underlined = false
        label.forEach { char ->
            if (!underlined && mnemonic != null && char.equals(mnemonic, ignoreCase = true)) {
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(char)
                }
                underlined = true
            } else append(char)
        }
    }
}