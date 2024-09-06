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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kaz4.composemessanger.domain.models.Avatars
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.ui.profile.components.ChooseProfilePicFromGallery
import com.kaz4.composemessanger.ui.profile.components.ProfileTextField
import com.kaz4.composemessanger.ui.profile.util.birthDateVisualTransformation
import com.kaz4.composemessanger.ui.profile.util.formatDateForServer
import com.kaz4.composemessanger.ui.profile.util.getZodiacSign
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

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

        LaunchedEffect(state.successMessage) {
            state.successMessage?.let { success ->
                Toast.makeText(context, success, Toast.LENGTH_SHORT).show()
                viewModel.clearSuccessMessage()
            }
        }

        LaunchedEffect(state.errorMessage) {
            state.errorMessage?.let { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
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
    val birthDate by remember { mutableStateOf(userProfile.birthday ?: "") }
    var bio by remember { mutableStateOf(userProfile.status) }
    val phoneNumber by remember { mutableStateOf(userProfile.phone) }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    val dateState = remember { mutableStateOf(userProfile.birthday ?: "") }
    var isDateFieldFocused by remember { mutableStateOf(false) }
    var zodiacSign by remember { mutableStateOf("") }

    var isManualInput by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(dateState.value) {
        val formattedDate = if (dateState.value.length == 8) {
            "${dateState.value.take(2)}.${dateState.value.substring(2, 4)}.${dateState.value.takeLast(4)}"
        } else {
            dateState.value
        }

        zodiacSign = getZodiacSign(formattedDate)
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
                        isManualInput = true
                        val formattedDate = newValue.take(8).filter { it.isDigit() }
                        dateState.value = formattedDate
                    },
                    keyboardType = KeyboardType.Number,
                    visualTransformation = if (isManualInput) birthDateVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            if (!isDateFieldFocused) {
                                dateState.value = ""
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

                ProfileTextField(
                    entry = bio,
                    hint = "About You",
                    onChange = { bio = it }
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
                modifier = Modifier.statusBarsPadding()
                    .padding(
                        top = MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.extraSmall
                    )
                    .clickable { onBackButton() }
            ) {
                Icon(
                    modifier = Modifier
                        .requiredSize(50.dp)
                        .padding(5.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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

@Preview(showBackground = true)
@Composable
fun PreviewDarkProfileScreen() {
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
        ComposeMessangerTheme (darkTheme = true) {
            ProfileContent(
                keyboardController = keyboardController,
                onProfileUpdate = { },
                userProfile = hardcodedProfileData,
                onBackButton = {}
            )
        }
    }
}
