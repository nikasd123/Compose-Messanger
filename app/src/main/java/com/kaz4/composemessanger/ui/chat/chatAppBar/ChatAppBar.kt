package com.kaz4.composemessanger.ui.chat.chatAppBar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    modifier: Modifier = Modifier,
    title: String = "Title",
    description: String = "Description",
    onMoreDropDownBlockUserClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    SmallTopAppBar(
        modifier = modifier,
        title = {
            Row {
                Surface(
                    modifier = modifier.size(50.dp),
                    shape = CircleShape,
                    color = Color.LightGray
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }
                Column(
                    modifier = modifier
                        .padding(start = MaterialTheme.spacing.small),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description"
            )
        },
        actions = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Videochat Clicked.\n(Not Available)",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                Icon(imageVector = Icons.Filled.VideoCall, contentDescription = null)
            }
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Voicechat Clicked.\n(Not Available)",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                Icon(imageVector = Icons.Filled.Call, contentDescription = null)
            }
            IconButton(
                onClick = {
                    expanded = true
                }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Block User")
                        },
                        onClick = {
                            onMoreDropDownBlockUserClick?.invoke()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.PriorityHigh,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChatAppBar() {
    ChatAppBar(
        title = "Chat Title",
        description = "Chat Description",
        onMoreDropDownBlockUserClick = { println("More options clicked") }
    )
}