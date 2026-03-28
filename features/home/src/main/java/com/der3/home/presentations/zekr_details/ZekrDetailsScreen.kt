package com.der3.home.presentations.zekr_details

import android.content.res.Configuration
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import com.der3.model.AppStyle
import com.der3.model.ShareZekrType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.shared.params.ZekrDetailsParams
import com.der3.home.di.factory.ZekrDetailsViewModelFactory
import com.der3.home.presentations.zekr_details.components.CategoryChip
import com.der3.home.presentations.zekr_details.components.ControlPanel
import com.der3.home.presentations.zekr_details.components.ProgressCard
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsIntent
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsState
import com.der3.home.utils.AzkarDetailsMenuItems
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.CircularZekrCounter
import com.der3.ui.components.CustomMenu
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.FontSizeBottomSheet
import com.der3.ui.components.LoadingDialog
import com.der3.ui.components.VolumeBottomSheet
import com.der3.ui.components.ShareBottomSheet
import com.der3.ui.components.TextSlider
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale
import com.der3.ui.components.captureComposable
import com.der3.ui.components.saveBitmapToCache
import com.der3.ui.components.ZekrShareCard
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.isStatusBarDark
import kotlinx.coroutines.launch


@Composable
fun ZekrDetailsRoute(
    params: ZekrDetailsParams, onNavigate: (Screens) -> Unit
) {

    val viewModel: ZekrDetailsViewModel =
        hiltViewModel<ZekrDetailsViewModel, ZekrDetailsViewModelFactory> { factory ->
            factory.create(params)
        }


    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiText?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> {
                    onNavigate(it.screen)
                }

                is MviEffect.Share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, it.text)
                        it.imageUri?.let { uriString ->
                            val uri = Uri.parse(uriString)
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        type = when (it.type) {
                            ShareZekrType.TEXT_ONLY -> "text/plain"
                            ShareZekrType.IMAGE_ONLY -> "image/*"
                            ShareZekrType.TEXT_AND_IMAGE -> "image/*"
                        }
                        if (android.os.Build.VERSION.SDK_INT >= 36) {
                            removeLaunchSecurityProtection()
                        }
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(shareIntent)
                }

                is MviEffect.CaptureAndShareImage -> {
                    scope.launch {
                        val currentState = viewModel.viewState
                        val bitmap = captureComposable(
                            context = context,
                            content = {
                                ZekrShareCard(
                                    text = currentState.zekrDetails.text,
                                    count = currentState.currentCount,
                                    total = currentState.zekrDetails.repeatCount
                                )
                            }
                        )
                        val uri = saveBitmapToCache(context, bitmap)
                        uri?.let {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_STREAM, it)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                type = "image/*"
                                if (android.os.Build.VERSION.SDK_INT >= 36) {
                                    removeLaunchSecurityProtection()
                                }
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(shareIntent)
                        }
                    }
                }

                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    errorType = it.error
                    showErrorDialog = true
                }
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog || viewModel.viewState.error != null,
        message = errorMessage ?: viewModel.viewState.error,
        onRetry = {
            viewModel.onIntent(ZekrDetailsIntent.Retry)
            showErrorDialog = false
            errorMessage = null
        },
        onDismiss = {
            viewModel.onIntent(ZekrDetailsIntent.DismissError)
            showErrorDialog = false
            errorMessage = null
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.screenBackground,
    )

    ZekrDetailsScreen(
        state = viewModel.viewState, onIntent = viewModel::onIntent
    )

}

@Composable
fun ZekrDetailsScreen(
    state: ZekrDetailsState,
    onIntent: (ZekrDetailsIntent) -> Unit,
) {


    val total = state.zekrDetails.repeatCount.coerceAtLeast(1)
    val progress = (state.currentCount.toFloat() / total.toFloat()).coerceIn(0f, 1f)

    val scrollState = rememberScrollState()

    LoadingDialog(visible = state.isLoading)

    ShareBottomSheet(
        isVisible = state.shareSheetVisibility,
        onDismiss = {
            onIntent(ZekrDetailsIntent.ShareSheetVisibility(isVisible = false))
        },
        onShareAsText = {
            onIntent(ZekrDetailsIntent.ShareAsText)
        },
        onShareAsImage = {
            onIntent(ZekrDetailsIntent.ShareAsImage)
        }
    )

    FontSizeBottomSheet(
        isVisible = state.fontSizeSheetVisibility,
        currentFontSize = state.zekrFontSize.toFloat(),
        onDismiss = {
            onIntent(ZekrDetailsIntent.FontSizeSheetVisibility(isVisible = false))
        },
        onSave = { font ->
            onIntent(ZekrDetailsIntent.UpdateFontSize(updateFont = font.toInt()))
        },
        onReset = {}
    )

    VolumeBottomSheet(
        isVisible = state.volumeSheetVisibility,
        currentVolume = state.currentVolume,
        onDismiss = {
            onIntent(ZekrDetailsIntent.VolumeSheetVisibility(isVisible = false))
        },
        onVolumeChange = { volume ->
            onIntent(ZekrDetailsIntent.UpdateVolume(volume = volume))
        },
        onSave = { volume ->
            onIntent(ZekrDetailsIntent.UpdateVolume(volume = volume))
            onIntent(ZekrDetailsIntent.VolumeSheetVisibility(isVisible = false))
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
    ) {
        Der3TopAppBar(
            title = stringResource(R.string.zekr_details_title),
            backgroundColor = AppColors.screenBackground,
            onBackClick = {
                onIntent(ZekrDetailsIntent.Back)
            },
            trailingContent = {
                IconButton(
                    onClick = {
                        onIntent(ZekrDetailsIntent.ExpandDropdownMenu(isExpand = true))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = AppColors.gray900Text
                    )
                }

                CustomMenu(
                    isVisible = state.isMenuExpanded,
                    onDismiss = {
                        onIntent(ZekrDetailsIntent.ExpandDropdownMenu(isExpand = false))
                    },
                    menuItems = state.menuItems, onMenuItemClicked = { item ->
                        when (item.id) {
                            is AzkarDetailsMenuItems.ZEKR_FONT_SIZE -> {
                                onIntent(ZekrDetailsIntent.ExpandDropdownMenu(isExpand = false))
                                onIntent(ZekrDetailsIntent.FontSizeSheetVisibility(isVisible = true))
                            }

                            is AzkarDetailsMenuItems.ZEKR_VOLUME -> {
                                onIntent(ZekrDetailsIntent.ExpandDropdownMenu(isExpand = false))
                                onIntent(ZekrDetailsIntent.VolumeSheetVisibility(isVisible = true))
                            }
                        }
                    }
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(20.dp))


            // Category Badge
            CategoryChip(
                text = state.zekrDetails.categoryName ?: stringResource(R.string.zekr_details_quran_verse)
            )

            Spacer(Modifier.height(24.dp))

            TextSlider(
                pageSize = state.zekrPageSize,
                fontSize = (state.zekrFontSize).sp,
                longText = state.zekrDetails.text
            )

            Spacer(Modifier.height(40.dp))

            // Circular Counter
            CircularZekrCounter(
                progress = progress,
                count = state.currentCount,
                total = state.zekrDetails.repeatCount,
                onClick = {
                    onIntent(ZekrDetailsIntent.IncrementZekrReadingCount)
                })

            Spacer(Modifier.height(60.dp))

            ProgressCard(
                progress = progress, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(40.dp))
        }

        ControlPanel(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
                .fillMaxWidth(0.92f),
            isPlaying = state.audioState.isPlaying,
            audioProgress = state.audioState.progress,
            isFavorite = state.zekrDetails.isFavorite,
            onFavorite = { onIntent(ZekrDetailsIntent.ToggleFavorite) },
            onPlay = {
                onIntent(
                    ZekrDetailsIntent.ToggleAudio(
                        audioPath = state.zekrDetails.audioPath
                    )
                )
            },
            onReset = {
                onIntent(ZekrDetailsIntent.ResetAudio)
            },
            onShare = {
                onIntent(ZekrDetailsIntent.ShareSheetVisibility(isVisible = true))
            },
            onVolume = {
                onIntent(ZekrDetailsIntent.VolumeSheetVisibility(isVisible = true))
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Composable
private fun ZekrDetailsScreenLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ZekrDetailsScreen(
            state = ZekrDetailsState(), onIntent = {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ZekrDetailsScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ZekrDetailsScreen(
            state = ZekrDetailsState(), onIntent = {})
    }
}

/**
 * UI click
↓
Intent.ToggleAudio
↓
UseCase
↓
AudioPlayer
↓
ObserveAzkarAudioStateUseCase
↓
Reducer updates state
↓
Compose recomposes
↓
Icon switches Play/Pause automatically*/
