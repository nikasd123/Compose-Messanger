package com.kaz4.composemessanger.ui.profile

import android.net.Uri
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kaz4.composemessanger.domain.models.User
import com.kaz4.composemessanger.domain.models.hardcodedUser
import com.kaz4.composemessanger.ui.profile.components.ChooseProfilePicFromGallery
import com.kaz4.composemessanger.ui.profile.components.ProfileTextField
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun ProfileScreen(navController: NavHostController){
    val keyboardController = LocalSoftwareKeyboardController.current
    if (keyboardController != null) {
        ProfileContent(
            keyboardController = keyboardController,
            onProfileUpdate = {  }
        )
    }


}

@Composable
fun ProfileContent(
    keyboardController: SoftwareKeyboardController,
    onProfileUpdate: (User) -> Unit
) {
    val userProfile = hardcodedUser

    var name by remember { mutableStateOf(userProfile.userName) }
    var surName by remember { mutableStateOf(userProfile.userSurName) }
    var bio by remember { mutableStateOf(userProfile.userBio) }
    var phoneNumber by remember { mutableStateOf(userProfile.userPhoneNumber) }
    var city by remember { mutableStateOf(userProfile.userCity) }
    var birthDate by remember { mutableStateOf(userProfile.birthDate) }
    var zodiacSign by remember { mutableStateOf(userProfile.zodiacSign) }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()

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
                    ChooseProfilePicFromGallery(userProfile.userProfilePictureUrl) { uri ->
                        avatarUri = uri
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(text = phoneNumber, style = MaterialTheme.typography.bodyMedium)

                ProfileTextField(name, "User Name", onChange = { name = it })

                ProfileTextField(city, "City", onChange = { city = it })

                ProfileTextField(birthDate, "Birth Date", onChange = { birthDate = it }, keyboardType = KeyboardType.Number)

                Text(text = "Zodiac Sign: $zodiacSign", style = MaterialTheme.typography.titleMedium)

                ProfileTextField(bio, "About You", onChange = { bio = it })

                Button(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.large)
                        .fillMaxWidth(),
                    onClick = {
                        val updatedUser = userProfile.copy(
                            userName = name,
                            userSurName = surName,
                            userCity = city,
                            birthDate = birthDate,
                            zodiacSign = zodiacSign,
                            userBio = bio,
                            userProfilePictureUrl = avatarUri?.toString() ?: userProfile.userProfilePictureUrl
                        )

                        onProfileUpdate(updatedUser)
                    }
                ) {
                    Text(text = "Save Profile", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    if (keyboardController != null) {
        ProfileContent(
            keyboardController = keyboardController,
            onProfileUpdate = {  }
        )
    }
}
