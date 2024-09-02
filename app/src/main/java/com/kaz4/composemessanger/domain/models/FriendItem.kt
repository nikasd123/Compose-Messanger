package com.kaz4.composemessanger.domain.models

data class FriendItem(
    val chatRoomUUID: String,
    val userName: String = "",
    val userUUID: String = "",
    val oneSignalUserId: String,
    val registerUUID: String = "",
    val userPictureUrl: String = "",
    val lastMessage: ChatMessage = ChatMessage()
)

val users = listOf(
    FriendItem(
        chatRoomUUID = "room1",
        userName = "имя",
        userUUID = "uuid1",
        oneSignalUserId = "signalId1",
        registerUUID = "regId1",
        userPictureUrl = "https://example.com/user1.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile1",
            message = "Hello!",
            date = System.currentTimeMillis(),
            status = "sent"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "received"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "uuid3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "received"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "delivered"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "read"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "delivered"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "read"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "delivered"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "read"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "delivered"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "read"
        )
    ),
    FriendItem(
        chatRoomUUID = "room2",
        userName = "муж",
        userUUID = "uuid2",
        oneSignalUserId = "signalId2",
        registerUUID = "regId2",
        userPictureUrl = "https://example.com/user2.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile2",
            message = "How are you?",
            date = System.currentTimeMillis(),
            status = "delivered"
        )
    ),
    FriendItem(
        chatRoomUUID = "room3",
        userName = "хз кто это",
        userUUID = "uuid3",
        oneSignalUserId = "signalId3",
        registerUUID = "regId3",
        userPictureUrl = "https://example.com/user3.jpg",
        lastMessage = ChatMessage(
            profileUUID = "profile3",
            message = "Good morning!",
            date = System.currentTimeMillis(),
            status = "read"
        )
    )
)
