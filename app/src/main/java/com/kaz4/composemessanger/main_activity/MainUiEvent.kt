package com.kaz4.composemessanger.main_activity

sealed class MainUiEvent {
    data object CheckAuth : MainUiEvent()
}