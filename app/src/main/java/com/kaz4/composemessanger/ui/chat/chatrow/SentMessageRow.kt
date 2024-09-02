package com.kaz4.composemessanger.ui.chat.chatrow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.domain.models.MessageStatus
import com.kaz4.composemessanger.ui.chat.chatrow.chatbubble.ChatBubbleConstraints
import com.kaz4.composemessanger.ui.chat.chatrow.chatbubble.TextMessageInsideBubble
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun SentMessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.extraSmall,
                bottom = MaterialTheme.spacing.extraSmall
            )
    ) {
        ChatBubbleConstraints(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { },
            content = {
                TextMessageInsideBubble(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.extraSmall,
                        end = MaterialTheme.spacing.extraSmall,
                        bottom = MaterialTheme.spacing.extraSmall
                    ),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    messageStat = {
                        MessageTimeText(
                            modifier = Modifier.wrapContentSize(),
                            messageTime = messageTime,
                            messageStatus = messageStatus
                        )
                    }
                )
            }
        )
    }
}