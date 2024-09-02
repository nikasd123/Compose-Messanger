package com.kaz4.composemessanger.ui.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.kaz4.composemessanger.domain.models.MessageRegister
import com.kaz4.composemessanger.domain.models.MessageStatus
import com.kaz4.composemessanger.domain.models.User
import com.kaz4.composemessanger.domain.models.hardcodedMessages
import com.kaz4.composemessanger.ui.chat.chatAppBar.ChatAppBar
import com.kaz4.composemessanger.ui.chat.chatinput.ChatInput
import com.kaz4.composemessanger.ui.chat.chatrow.ReceivedMessageRow
import com.kaz4.composemessanger.ui.chat.chatrow.SentMessageRow
import com.kaz4.composemessanger.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController
) {
    val messages = hardcodedMessages
    val opponentProfile = User(
        userName = "John",
        userSurName = "Doe",
        userProfilePictureUrl = "",
        status = "online"
    )

    ChatScreenContent(
        modifier,
        keyboardController,
        messages,
        opponentProfile
    )
}

@Composable
fun ChatScreenContent(
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController,
    messages: List<MessageRegister>,
    opponentProfile: User
) {
    val opponentName = opponentProfile.userName
    val opponentSurname = opponentProfile.userSurName
    val opponentStatus = opponentProfile.status

    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = messages.size)
    var isChatInputFocus by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = messages) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(index = messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current

        ChatAppBar(
            title = "$opponentName $opponentSurname",
            description = opponentStatus.lowercase(),
            onMoreDropDownBlockUserClick = {
                Toast.makeText(context, "User Blocked", Toast.LENGTH_SHORT).show()
            }
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
                    bottom = MaterialTheme.spacing.medium),
            onMessageChange = { messageContent ->
                Toast.makeText(context, "Message Sent: $messageContent", Toast.LENGTH_SHORT).show()
            },
            onFocusEvent = { isChatInputFocus = it }
        )
    }
}