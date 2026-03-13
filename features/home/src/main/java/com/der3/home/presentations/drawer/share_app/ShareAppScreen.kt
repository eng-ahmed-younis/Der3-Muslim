package com.der3.home.presentations.drawer.share_app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppIntent
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import androidx.compose.ui.res.stringResource
import com.der3.home.presentations.drawer.share_app.components.ShareIconItem
import com.der3.home.utils.shareTypeItems
import com.der3.model.ShareAppType
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
fun ShareAppRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel: ShareAppViewModel = hiltViewModel<ShareAppViewModel>()
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

    LaunchedEffect(viewModel.viewState.error) {
        viewModel.viewState.error?.let {
            errorMessage = it
            showErrorDialog = true
        }
    }

    LoadingDialog(visible = viewModel.viewState.isLoading)

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(ShareAppIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(ShareAppIntent.DismissError)
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )

    ShareDer3AppScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun ShareDer3AppScreen(
    state: ShareAppState,
    onIntent: (ShareAppIntent) -> Unit
) {
    val context = LocalContext.current
    val colors = AppColors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.gray50)
    ) {
        // Custom App Bar
        Der3TopAppBar(
            title = stringResource(id = R.string.share_app_title),
            backgroundColor = colors.gray50,
            onBackClick = { onIntent(ShareAppIntent.Back) },
            titleColor = colors.gray900Text,
            navigationIconColor = colors.gray900Text
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Green Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = colors.green800),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Shield Icon in Circle
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(colors.gold500.copy(alpha = 0.3f))
                            .padding()
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShieldMoon,
                            contentDescription = null,
                            modifier = Modifier.size(45.dp),
                            tint = colors.gold500
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.share_app_card_title),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code Style Placeholder
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Dummy QR Grid representation
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            repeat(5) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(5) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(colors.green100.copy(alpha = 0.5f))
                                        )
                                    }
                                }
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = colors.green800,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = stringResource(id = R.string.share_app_card_subtitle),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        letterSpacing = 1.5.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.share_app_via_label),
                color = colors.gray500,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Social Sharing Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(shareTypeItems.size){
                    ShareIconItem(
                        label = shareTypeItems[it].label,
                        icon = shareTypeItems[it].icon,
                        onClick = {
                            when(shareTypeItems[it].type){
                                ShareAppType.WHATS_APP -> onIntent(ShareAppIntent.ShareViaWhatsApp(state.downloadLink))
                                ShareAppType.TELEGRAM -> onIntent(ShareAppIntent.ShareViaTelegram(state.downloadLink))
                                ShareAppType.FACEBOOK -> onIntent(ShareAppIntent.ShareViaFacebook(state.downloadLink))
                                ShareAppType.TWITTER -> onIntent(ShareAppIntent.ShareViaTwitter(state.downloadLink))
                                else -> {}
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Copy Link Button
            val successMessage = stringResource(id = R.string.copy_link_success)
            Button(
                onClick = {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Download Link", state.downloadLink)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                    onIntent(ShareAppIntent.CopyLink(state.downloadLink))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.gold500)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = colors.green900,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.copy_link_button),
                        color = colors.green900,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.share_app_description),
                color = colors.gray400,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}



@Preview(showBackground = true, name = "Arabic", locale = "ar")
@Composable
fun ShareDer3AppScreenPreviewAr() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ShareDer3AppScreen(
            state = ShareAppState(),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "English")
@Composable
fun ShareDer3AppScreenPreviewEn() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("en").build()
    ) {
        ShareDer3AppScreen(
            state = ShareAppState(),
            onIntent = {}
        )
    }
}
