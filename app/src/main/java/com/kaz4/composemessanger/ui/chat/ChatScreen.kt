package com.kaz4.composemessanger.ui.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kaz4.composemessanger.R
import com.kaz4.composemessanger.domain.models.MessageRegister
import com.kaz4.composemessanger.domain.models.MessageStatus
import com.kaz4.composemessanger.domain.models.User
import com.kaz4.composemessanger.domain.models.hardcodedMessages
import com.kaz4.composemessanger.domain.models.users
import com.kaz4.composemessanger.ui.chat.chatAppBar.ChatAppBar
import com.kaz4.composemessanger.ui.chat.chatinput.ChatInput
import com.kaz4.composemessanger.ui.chat.chatrow.ReceivedMessageRow
import com.kaz4.composemessanger.ui.chat.chatrow.SentMessageRow
import com.kaz4.composemessanger.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen(
    navController: NavHostController,
    registerUUID: String
) {
    val user = users.find { it.registerUUID == registerUUID }

    if (user == null) {
        Text(text = stringResource(id = R.string.user_not_found))
        return
    }

    val opponentProfile = User(
        userName = user.userName,
        userSurName = "",
        userProfilePictureUrl = user.userPictureUrl,
        status = stringResource(id = R.string.online)
    )

    val messages = hardcodedMessages

    ChatScreenContent(
        messages = messages,
        opponentProfile = opponentProfile,
        navController = navController
    )
}

@Composable
fun ChatScreenContent(
    messages: List<MessageRegister>,
    opponentProfile: User,
    navController: NavHostController
) {
    val opponentName = opponentProfile.userName
    val opponentStatus = opponentProfile.status

    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = messages.size)

    val userBlocked = stringResource(id = R.string.user_blocked)

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(index = messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current

        ChatAppBar(
            title = opponentName,
            description = opponentStatus.lowercase(),
            onMoreDropDownBlockUserClick = {
                Toast.makeText(context, userBlocked, Toast.LENGTH_SHORT).show()
            },
            navController = navController
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState
        ) {
            items(messages) { message: MessageRegister ->
                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

                when (message.isMessageFromOpponent) {
                    true -> {
                        ReceivedMessageRow(
                            text = message.chatMessage.message,
                            opponentName = opponentName,
                            messageTime = sdf.format(message.chatMessage.date),
                        )
                    }
                    false -> {
                        SentMessageRow(
                            text = message.chatMessage.message,
                            messageTime = sdf.format(message.chatMessage.date),
                            messageStatus = MessageStatus.valueOf(message.chatMessage.status)
                        )
                    }
                }
            }
        }

        ChatInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.medium
                ),
            onMessageChange = { messageContent ->
                Toast.makeText(context, "Message Sent: $messageContent", Toast.LENGTH_SHORT).show()
            },
            onFocusEvent = {}
        )
    }
}