package com.kaz4.composemessanger.domain.models

data class User(
    var userName: String = "",
    var userEmail: String = "",
    var userPhoneNumber: String = "",
    var userProfilePictureUrl: String = "",
    var userBio: String = "",
    var userCity: String = "",
    val birthDate: String = "",
    val zodiacSign: String = "",
    var profileUUID: String = "",
    var oneSignalUserId: String = "",
    var userSurName: String = "",
    var status: String = ""
)

val hardcodedUser = User(
    userName = "John Doe",
    userEmail = "john.doe@example.com",
    userPhoneNumber = "+1234567890",
    userProfilePictureUrl = "https://example.com/profile.jpg",
    userBio = "Software Developer with a passion for creating amazing apps.",
    userCity = "New York",
    birthDate = "1990-01-01",
    zodiacSign = "Capricorn",
    profileUUID = "uuid-1234-5678-9012",
    oneSignalUserId = "onesignal-1234",
    userSurName = "Doe",
    status = "Active"
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