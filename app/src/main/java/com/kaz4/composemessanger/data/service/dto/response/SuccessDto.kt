package com.kaz4.composemessanger.data.service.dto.response

import com.google.gson.annotations.SerializedName

data class SuccessDto(
    @SerializedName("is_success")
    val isSuccess: Boolean?
)
