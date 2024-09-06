package com.kaz4.composemessanger.domain.models

import com.kaz4.composemessanger.data.service.dto.request.AvatarDto

data class Avatar(
    val filename: String,
    val base64: String
)

internal fun Avatar.toDto() = AvatarDto(
    filename = filename,
    base64 = base64
)