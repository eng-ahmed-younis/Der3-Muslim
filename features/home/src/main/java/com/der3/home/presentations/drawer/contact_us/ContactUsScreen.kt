package com.der3.home.presentations.drawer.contact_us

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.presentations.drawer.contact_us.components.ContactInfoCard
import com.der3.home.presentations.drawer.contact_us.components.ContactTextField
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsIntent
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun ContactUsRoute(
    onNavigate: (Screens) -> Unit = {},
) {
    val viewModel = hiltViewModel<ContactUsViewModel>()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    showErrorDialog = true
                }
                else -> {}
            }
        }.launchIn(this)
    }

    LoadingDialog(visible = viewModel.viewState.isLoading)

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(ContactUsIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(ContactUsIntent.DismissError)
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.green25,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )

    ContactUsScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun ContactUsScreen(
    state: ContactUsState,
    onIntent: (ContactUsIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.green25)
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.contact_title),
            backgroundColor = Color.Transparent,
            onBackClick = {
                onIntent(ContactUsIntent.Back)
            },
            trailingContent = {
                IconButton(onClick = { onIntent(ContactUsIntent.ShareApp) }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = AppColors.gray900Text
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.green800)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = stringResource(id = R.string.contact_us_header_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppColors.white,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.contact_us_header_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.white.copy(alpha = 0.7f),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ContactTextField(
                        label = stringResource(id = R.string.contact_name_label),
                        value = state.name,
                        onValueChange = { onIntent(ContactUsIntent.OnNameChange(it)) },
                        placeholder = stringResource(id = R.string.contact_name_hint)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ContactTextField(
                        label = stringResource(id = R.string.contact_email_label),
                        value = state.email,
                        onValueChange = { onIntent(ContactUsIntent.OnEmailChange(it)) },
                        placeholder = stringResource(id = R.string.contact_email_hint)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ContactTextField(
                        label = stringResource(id = R.string.contact_message_label),
                        value = state.message,
                        onValueChange = { onIntent(ContactUsIntent.OnMessageChange(it)) },
                        placeholder = stringResource(id = R.string.contact_message_hint),
                        singleLine = false,
                        minLines = 4
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onIntent(ContactUsIntent.SendMessage) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.green800)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = stringResource(id = R.string.contact_send_button),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Info Cards
            ContactInfoCard(
                title = stringResource(id = R.string.contact_email_card_title),
                subtitle = state.officialEmail,
                icon = Icons.Default.Email,
                onClick = { onIntent(ContactUsIntent.SendEmail) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactInfoCard(
                title = stringResource(id = R.string.contact_website_card_title),
                subtitle = state.website,
                icon = Icons.Default.Language,
                onClick = { onIntent(ContactUsIntent.OpenWebsite) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.contact_follow_us),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.gray500
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ContactSocialIcon(icon = Icons.Default.Language) { onIntent(ContactUsIntent.OpenSocialMedia) }
                ContactSocialIcon(icon = Icons.Default.AlternateEmail) { onIntent(ContactUsIntent.OpenSocialMedia) }
                ContactSocialIcon(icon = Icons.Default.CameraAlt) { onIntent(ContactUsIntent.OpenSocialMedia) }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}



@Composable
fun ContactSocialIcon(icon: ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = AppColors.green50.copy(alpha = 0.5f),
        modifier = Modifier.size(48.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.gray900Text,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactSocialIconPreview() {
    Der3MuslimTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ContactSocialIcon(icon = Icons.Default.Language) {}
        }
    }
}

@Preview(showBackground = true, name = "Arabic", locale = "ar")
@Composable
fun ContactUsScreenPreviewAr() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ContactUsScreen(
            state = ContactUsState(),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "English")
@Composable
fun ContactUsScreenPreviewEn() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("en").build()
    ) {
        ContactUsScreen(
            state = ContactUsState(),
            onIntent = {}
        )
    }
}
