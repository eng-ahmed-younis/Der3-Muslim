package com.der3.home.presentations.masbaha

import LoadingDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.home.presentations.masbaha.components.AzkarAutoSelected
import com.der3.home.presentations.masbaha.components.MasbahaActionButton
import com.der3.home.presentations.masbaha.components.MasbahaToggleActionButton
import com.der3.home.presentations.masbaha.components.ResetMasbahaDialog
import com.der3.home.presentations.masbaha.components.TargetDialog
import com.der3.home.presentations.masbaha.mvi.MasbahaIntent
import com.der3.home.presentations.masbaha.mvi.MasbahaState
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun MasbahaRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel: MasbahaViewModel = hiltViewModel<MasbahaViewModel>()
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiText?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    errorType = it.error
                    showErrorDialog = true
                }
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {},
        onDismiss = {}
    )


    MasbahaScreen(
        state = viewModel.viewState,
        onIntent = { intent ->
            if (intent is MasbahaIntent.Back) {
                onNavigate(Screens.Back())
            } else {
                viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasbahaScreen(
    state: MasbahaState,
    onIntent: (MasbahaIntent) -> Unit
) {
    var showTargetDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    if (showTargetDialog) {
        TargetDialog(
            currentTarget = state.targetCount,
            onDismiss = { showTargetDialog = false },
            onConfirm = {
                onIntent(MasbahaIntent.SetTargetCount(it))
                showTargetDialog = false
            }
        )
    }

    if (showResetDialog) {
        ResetMasbahaDialog(
            onDismiss = { showResetDialog = false },
            onConfirm = {
                onIntent(MasbahaIntent.ResetCount)
                showResetDialog = false
            }
        )
    }

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.electronic_rosary_title),
            backgroundColor = AppColors.gray50,
            onBackClick = {
                onIntent(MasbahaIntent.Back)
            },
            trailingContent = {
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = AppColors.gray900Text
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(AppColors.white)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.history_title),
                                    color = AppColors.gray900Text
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = AppColors.green800
                                )
                            },
                            onClick = {
                                showMenu = false
                                onIntent(MasbahaIntent.OpenHistory)
                            }
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Auto Switch and Label
        AzkarAutoSelected(
            autoSwitch = state.autoSwitch,
            onAutoSwitchChange = { onIntent(MasbahaIntent.ToggleAutoSwitch(it)) },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Azkar Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(state.azkars) { azkar ->
                val isSelected = state.selectedAzkar?.id == azkar.id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (isSelected) AppColors.green800 else AppColors.white)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) AppColors.green800 else AppColors.green50,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onIntent(MasbahaIntent.SelectAzkar(azkar)) }
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = azkar.text ?: "",
                        color = if (isSelected) AppColors.white else AppColors.green700,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val progressText = if (state.targetCount == -1) {
                "-"
            } else {
                "%${state.progressPercentage}"
            }
            Text(
                text = "${stringResource(id = R.string.progress_label)}: $progressText",
                color = AppColors.gold600,
                style = MaterialTheme.typography.bodyMedium
            )
            val targetText = if (state.targetCount == -1) {
                stringResource(id = R.string.target_unlimited)
            } else {
                state.targetCount.toString()
            }
            Text(
                text = "${stringResource(id = R.string.target_label)}: $targetText",
                color = AppColors.green800,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { if (state.targetCount == -1) 0f else state.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp),
            color = AppColors.green800,
            trackColor = AppColors.gray200,
        )

        Spacer(modifier = Modifier.weight(1f))


        // Counter Circle
        val haptic = LocalHapticFeedback.current
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(color = AppColors.green800.copy(alpha = 0.05f), shape = CircleShape)
                .padding(20.dp)
                .background(color = AppColors.green800.copy(alpha = 0.1f), shape = CircleShape)
                .padding(20.dp)
                .clip(CircleShape)
                .background(color = AppColors.green800)
                .clickable {
                    if (state.isVibrationEnabled) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    onIntent(MasbahaIntent.IncrementCount)
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = state.currentCount.toString(),
                    color = AppColors.white,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.press_to_praise),
                    color = AppColors.white.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MasbahaActionButton(
                text = stringResource(id = R.string.reset),
                icon = Icons.Default.Refresh,
                modifier = Modifier.weight(1f),
                onClick = { showResetDialog = true }
            )
            MasbahaActionButton(
                text = stringResource(id = R.string.edit_target),
                icon = Icons.Default.Settings,
                modifier = Modifier.weight(1f),
                onClick = { showTargetDialog = true }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MasbahaToggleActionButton(
                text = stringResource(id = if (state.isSoundEnabled) R.string.sound_on else R.string.sound_off),
                icon = Icons.AutoMirrored.Filled.VolumeUp,
                enabled = state.isSoundEnabled,
                modifier = Modifier.weight(1f),
                onToggle = { onIntent(MasbahaIntent.ToggleSound(it)) }
            )
            MasbahaToggleActionButton(
                text = stringResource(id = if (state.isVibrationEnabled) R.string.vibration_on else R.string.vibration_off),
                icon = Icons.Default.Vibration,
                enabled = state.isVibrationEnabled,
                modifier = Modifier.weight(1f),
                onToggle = { onIntent(MasbahaIntent.ToggleVibration(it)) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MasbahaScreenPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        MasbahaScreen(
            state = MasbahaState(
                azkars = listOf(
                    MasbahaAzkar(1, "سبحان الله", 33),
                    MasbahaAzkar(2, "الحمد لله", 33),
                    MasbahaAzkar(3, "الله أكبر", 33),
                    MasbahaAzkar(4, "سبحان الله", 33),
                    MasbahaAzkar(5, "الحمد لله", 33),
                    MasbahaAzkar(6, "الله أكبر", 33)
                ),
                currentCount = 21,
                targetCount = 33,
                selectedAzkar = MasbahaAzkar(id = 3)
            ),
            onIntent = {}
        )
    }
}
