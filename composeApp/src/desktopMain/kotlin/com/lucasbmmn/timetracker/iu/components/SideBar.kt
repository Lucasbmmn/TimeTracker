package com.lucasbmmn.timetracker.iu.components

import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.themes.LocalTheme

@Composable
fun SideBar(content: @Composable () -> Unit) {
    val theme = LocalTheme.current
    val verticalScrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxHeight()
            .background(theme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .verticalScroll(verticalScrollState)
                .fillMaxHeight()
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .width(150.dp)
        ) {
            content()
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(verticalScrollState),
            style = ScrollbarStyle(
                minimalHeight = 16.dp,
                thickness = 8.dp,
                shape = RoundedCornerShape(4.dp),
                hoverDurationMillis = 300,
                unhoverColor = theme.primary.copy(alpha = 0.25f),
                hoverColor = theme.primary.copy(alpha = 0.75f)
            ),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }

    VerticalDivider(
        modifier = Modifier,
        color = theme.background,
        thickness = 1.dp,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SideBarItem(
    isSelected: Boolean,
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit,
) {
    val theme = LocalTheme.current
    var isHovered by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) theme.highlight else if (isHovered) theme.highlightLight else theme.surface)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .onPointerEvent(PointerEventType.Press) { if (it.buttons.isPrimaryPressed) onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        icon()
        Text(
            text = text,
            color = theme.onSurface,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
    }
}