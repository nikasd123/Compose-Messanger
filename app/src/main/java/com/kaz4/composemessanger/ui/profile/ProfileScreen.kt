package com.kaz4.composemessanger.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kaz4.composemessanger.domain.models.Avatars
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.ui.profile.components.ChooseProfilePicFromGallery
import com.kaz4.composemessanger.ui.profile.components.ProfileTextField
import com.kaz4.composemessanger.ui.profile.util.DateVisualTransformation
import com.kaz4.composemessanger.ui.profile.util.formatDateForServer
import com.kaz4.composemessanger.ui.profile.util.formatDateInputWithCursor
import com.kaz4.composemessanger.ui.profile.util.getZodiacSign
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(ProfileIntent.LoadUserProfile)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.userProfile != null) {
            val keyboardController = LocalSoftwareKeyboardController.current
            if (keyboardController != null) {
                ProfileContent(
                    userProfile = state.userProfile!!,
                    keyboardController = keyboardController,
                    onProfileUpdate = { updatedProfile ->
                        viewModel.processIntent(ProfileIntent.UpdateUserProfile(updatedProfile))
                    },
                    onBackButton = { navController.navigate("chatList") }
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        state.errorMessage?.let { error ->
            Toast.makeText(LocalContext.current, error, Toast.LENGTH_SHORT).show()
        }

        state.successMessage?.let { success ->
            Toast.makeText(LocalContext.current, success, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ProfileContent(
    userProfile: ProfileData,
    keyboardController: SoftwareKeyboardController,
    onProfileUpdate: (ProfileData) -> Unit,
    onBackButton: () -> Unit
) {
    var name by remember { mutableStateOf(userProfile.name) }
    var city by remember { mutableStateOf(userProfile.city) }
    var birthDate by remember { mutableStateOf(userProfile.birthday ?: "") }
    var bio by remember { mutableStateOf(userProfile.status ?: "") }
    var phoneNumber by remember { mutableStateOf(userProfile.phone ?: "") }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    val dateState = remember { mutableStateOf(birthDate) }
    val cursorPosition = remember { mutableStateOf(0) }
    var isDateFieldFocused by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val zodiacSign by remember {
        derivedStateOf { getZodiacSign(formatDateForServer(dateState.value)) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .imePadding()
    ) {
        Surface(
            modifier = Modifier
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    ChooseProfilePicFromGallery(userProfile.avatar) { uri ->
                        avatarUri = uri
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(text = phoneNumber, style = MaterialTheme.typography.bodyMedium)

                ProfileTextField(entry = name, hint = "User Name", onChange = { name = it })
                ProfileTextField(entry = city, hint = "City", onChange = { city = it })
                ProfileTextField(
                    entry = dateState.value,
                    hint = "Birth Date",
                    onChange = { newValue ->
                        val (formattedDate, newCursorPosition) = formatDateInputWithCursor(
                            newValue,
                            cursorPosition.value
                        )
                        dateState.value = formattedDate
                        cursorPosition.value = newCursorPosition
                    },
                    keyboardType = KeyboardType.Number,
                    visualTransformation = DateVisualTransformation(),
                    keyboardController = keyboardController,
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            if (!isDateFieldFocused) {
                                dateState.value = ""
                                cursorPosition.value = 0
                                isDateFieldFocused = true
                            }
                        } else {
                            isDateFieldFocused = false
                        }
                    }

                )
                Text(
                    text = "Zodiac Sign: $zodiacSign",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.medium)
                        .fillMaxWidth(),
                    onClick = {
                        val updatedUser = userProfile.copy(
                            name = name,
                            city = city,
                            birthday = formatDateForServer(dateState.value),
                            status = bio,
                            avatar = avatarUri?.toString() ?: userProfile.avatar
                        )
                        onProfileUpdate(updatedUser)
                    }
                ) {
                    Text(text = "Save Profile")
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable { onBackButton() }
            ) {
                Icon(
                    modifier = Modifier
                        .requiredSize(50.dp)
                        .padding(5.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val hardcodedProfileData = ProfileData(
        name = "John Doe",
        username = "johndoe",
        birthday = "1990-01-01",
        city = "New York",
        vk = "https://vk.com/johndoe",
        instagram = "https://instagram.com/johndoe",
        status = "Active",
        avatar = "https://example.com/avatar.jpg",
        id = 123,
        last = "2024-09-04T12:03:39.722Z",
        online = true,
        created = "2024-01-01T12:00:00.000Z",
        phone = "+1234567890",
        completedTask = 42,
        avatars = Avatars(
            avatar = "https://example.com/avatar.jpg",
            bigAvatar = "https://example.com/big_avatar.jpg",
            miniAvatar = "https://example.com/mini_avatar.jpg",
            filename = "",
            base64 = ""
        )
    )


    if (keyboardController != null) {
        ProfileContent(
            keyboardController = keyboardController,
            onProfileUpdate = { },
            userProfile = hardcodedProfileData,
            onBackButton = {}
        )
    }
}
