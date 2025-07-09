package com.lucasbmmn.timetracker.iu.clients

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Language
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
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lucasbmmn.timetracker.iu.components.DropDownMenuItem
import com.lucasbmmn.timetracker.iu.components.DropdownMenu
import com.lucasbmmn.timetracker.iu.themes.LocalTheme
import com.lucasbmmn.timetracker.model.Client
import com.lucasbmmn.timetracker.util.Strings

@Composable
fun ClientsScreen(viewModel: ClientsViewModel = remember { ClientsViewModel() }) {
    val theme = LocalTheme.current
    val clients = viewModel.clients
    var clientBeingEdited by remember { mutableStateOf<Client?>(null) }

    if (clientBeingEdited == null) {
        val verticalScrollState = rememberScrollState()
        val horizontalScrollState = rememberScrollState()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
                    .padding(16.dp)
            ) {
                clients.forEach { client ->
                    Client(
                        client = client,
                        onEdit = { clientBeingEdited = it },
                        onDelete = { viewModel.deleteClient(it) },
                    )
                }
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
            HorizontalScrollbar(
                adapter = rememberScrollbarAdapter(horizontalScrollState),
                style = ScrollbarStyle(
                    minimalHeight = 16.dp,
                    thickness = 8.dp,
                    shape = RoundedCornerShape(4.dp),
                    hoverDurationMillis = 300,
                    unhoverColor = theme.primary.copy(alpha = 0.25f),
                    hoverColor = theme.primary.copy(alpha = 0.75f)
                ),
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
            )
        }
    } else {
        clientBeingEdited?.let { client ->
            EditClientScreen(
                client = client,
                onCancel = { clientBeingEdited = null },
                onSave = {
                    viewModel.editClient(it)
                    clientBeingEdited = null
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Client(
    client: Client,
    onEdit: (Client) -> Unit = {},
    onDelete: (Client) -> Unit = {},
) {
    val theme = LocalTheme.current
    var isHovered by remember { mutableStateOf(false) }
    var isContextMenuExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isHovered) theme.highlightLight else theme.surface)
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .onPointerEvent(PointerEventType.Press) { if (it.buttons.isSecondaryPressed) isContextMenuExpanded = !isContextMenuExpanded }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = client.company,
                color = theme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = client.name,
                color = theme.onSurface,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        Icons.Rounded.Call,
                        contentDescription = null,
                        tint = theme.primary,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = client.phoneNumber,
                        color = theme.onSurface,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        Icons.Rounded.AlternateEmail,
                        contentDescription = null,
                        tint = theme.primary,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = client.email,
                        color = theme.onSurface,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        Icons.Rounded.Language,
                        contentDescription = null,
                        tint = theme.primary,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = client.timezone,
                        color = theme.onSurface,
                    )
                }
            }
        }

        DropdownMenu(
            expanded = isContextMenuExpanded,
            onDismissRequest = { isContextMenuExpanded = false },
        ) {
            DropDownMenuItem(
                label = Strings.get("context_menu.edit"),
            ) { onEdit(client) }
            DropDownMenuItem(
                label = Strings.get("context_menu.delete"),
            ) { onDelete(client) }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditClientScreen(
    client: Client,
    onCancel: () -> Unit,
    onSave: (Client) -> Unit
) {
    val theme = LocalTheme.current
    val verticalScrollState = rememberScrollState()
    var name by remember { mutableStateOf(client.name) }
    var company by remember { mutableStateOf(client.company) }
    var email by remember { mutableStateOf(client.email) }
    var phoneNumber by remember { mutableStateOf(client.phoneNumber) }
    var timezone by remember { mutableStateOf(client.timezone) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = Strings.get("client.edit"),
            color = theme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(16.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .padding(16.dp)
                    .fillMaxWidth()

            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = Strings.get("client.name")) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = theme.onBackground,
                        cursorColor = theme.onBackground,
                        focusedLabelColor = theme.onBackground,
                        focusedBorderColor = theme.primary,
                        unfocusedLabelColor = theme.onBackground,
                        unfocusedBorderColor = theme.outline,
                    )
                )
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text(text = Strings.get("client.company")) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = theme.onBackground,
                        cursorColor = theme.onBackground,
                        focusedLabelColor = theme.onBackground,
                        focusedBorderColor = theme.primary,
                        unfocusedLabelColor = theme.onBackground,
                        unfocusedBorderColor = theme.outline,
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = Strings.get("client.email")) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = theme.onBackground,
                        cursorColor = theme.onBackground,
                        focusedLabelColor = theme.onBackground,
                        focusedBorderColor = theme.primary,
                        unfocusedLabelColor = theme.onBackground,
                        unfocusedBorderColor = theme.outline,
                    )
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(text = Strings.get("client.phone_number")) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = theme.onBackground,
                        cursorColor = theme.onBackground,
                        focusedLabelColor = theme.onBackground,
                        focusedBorderColor = theme.primary,
                        unfocusedLabelColor = theme.onBackground,
                        unfocusedBorderColor = theme.outline,
                    )
                )
                OutlinedTextField(
                    value = timezone,
                    onValueChange = { timezone = it },
                    label = { Text(text = Strings.get("client.timezone")) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = theme.onBackground,
                        cursorColor = theme.onBackground,
                        focusedLabelColor = theme.onBackground,
                        focusedBorderColor = theme.primary,
                        unfocusedLabelColor = theme.onBackground,
                        unfocusedBorderColor = theme.outline,
                    )
                )
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = Strings.get("dialog.cancel"),
                color = theme.onBackground,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, theme.outline, RoundedCornerShape(6.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .onPointerEvent(PointerEventType.Press, onEvent = { if (it.buttons.isPrimaryPressed) onCancel() })
            )
            Text(
                text = Strings.get("dialog.save"),
                color = theme.onPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = theme.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .onPointerEvent(
                        PointerEventType.Press,
                        onEvent = {
                            if (it.buttons.isPrimaryPressed) onSave(Client(client.uuid, name, company, email, phoneNumber, timezone))
                        })
            )
        }
    }
}
