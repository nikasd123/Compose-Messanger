package com.kaz4.composemessanger.domain.models

data class MessageRegister(
    var chatMessage: ChatMessage,
    var isMessageFromOpponent: Boolean
)
