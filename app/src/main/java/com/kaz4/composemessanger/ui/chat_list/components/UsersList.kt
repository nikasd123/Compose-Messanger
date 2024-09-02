package com.kaz4.composemessanger.ui.chat_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.domain.models.FriendItem
import com.kaz4.composemessanger.domain.models.MessageStatus
import com.kaz4.composemessanger.domain.models.users
import com.kaz4.composemessanger.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AcceptPendingRequestList(
    onclick: () -> Unit = {}
){
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
    ){
        items(users) { item ->
            AcceptPendingRequestListView(item)
        }
    }

}

@Composable
fun AcceptPendingRequestListView(
    item: FriendItem,
    onclick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onclick() }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.small
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .aspectRatio(1f)
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }
                if (item.lastMessage.status == "received" && item.lastMessage.profileUUID == item.userUUID) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = MaterialTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = item.userName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Text(
                                text = "Last Message: ${item.lastMessage.message}",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        Column {
                            Text(
                                text = sdf.format(item.lastMessage.date),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )

                            Icon(
                                imageVector = Icons.Filled.Email,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                } else {
                    val dateTimeControl: Long = 0
                    if (item.lastMessage.date != dateTimeControl) {
                        if (item.lastMessage.profileUUID != item.userUUID) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = item.userName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Me: ${item.lastMessage.message}",
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                                Text(
                                    text = sdf.format(item.lastMessage.date),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = item.userName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Last Message: ${item.lastMessage.message}",
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                                Text(
                                    text = sdf.format(item.lastMessage.date),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = item.userName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAcceptPendingRequestList() {
    AcceptPendingRequestList(
        onclick = {}
    )
}