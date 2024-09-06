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