package com.kaz4.composemessanger.domain.models

data class User(
    var profileUUID: String = "",
    var userEmail: String = "",
    var oneSignalUserId: String = "",
    var userName: String = "",
    var userProfilePictureUrl: String = "",
    var userSurName: String = "",
    var userBio: String = "",
    var userPhoneNumber: String = "",
    var status: String = ""
)

val hardcodedMessages = listOf(
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile1",
            message = "Hello!",
            date = System.currentTimeMillis() - 60000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis() - 30000,
            status = "RECEIVED"
        ),
        isMessageFromOpponent = false
    ),
    MessageRegister(
        chatMessage = ChatMessage(
            profileUUID = "profile3",
            message = "I'm good, thanks!",
            date = System.currentTimeMillis(),
            status = "READ"
        ),
        isMessageFromOpponent = true
    )
)