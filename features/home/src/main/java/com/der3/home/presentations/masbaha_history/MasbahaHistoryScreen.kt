package com.der3.home.presentations.masbaha_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.presentations.masbaha_history.components.ActivityItem
import com.der3.home.presentations.masbaha_history.components.FilterTabs
import com.der3.home.presentations.masbaha_history.components.RecentActivityHeader
import com.der3.home.presentations.masbaha_history.components.SummaryCard
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryIntent
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryState
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity
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
fun MasbahaHistoryRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel = hiltViewModel<MasbahaHistoryViewModel>()
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
                else -> {}
            }
        }.launchIn(scope)
    }

    ShiftSystemBarStyle(
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            viewModel.handleIntent(MasbahaHistoryIntent.LoadHistory)
            showErrorDialog = false
        },
        onDismiss = { showErrorDialog = false }
    )

    MasbahaHistoryScreen(
        state = viewModel.viewState,
        onIntent = { intent ->
            if (intent is MasbahaHistoryIntent.Back) {
                onNavigate(Screens.Back())
            } else {
                viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasbahaHistoryScreen(
    state: MasbahaHistoryState,
    onIntent: (MasbahaHistoryIntent) -> Unit = {}
) {
    var showClearDialog by remember { mutableStateOf(false) }

    if (showClearDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showClearDialog = false },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        onIntent(MasbahaHistoryIntent.ClearHistory)
                        showClearDialog = false
                    }
                ) {
                    Text(stringResource(id = R.string.clear), color = Color.Red)
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showClearDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            title = { Text(stringResource(id = R.string.clear_history)) },
            text = { Text(stringResource(id = R.string.clear_history_confirmation_message)) }
        )
    }

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {
        var showMenu by remember { mutableStateOf(false) }

        Der3TopAppBar(
            title = stringResource(id = R.string.history_title),
            backgroundColor = AppColors.gray50,
            onBackClick = {
                onIntent(MasbahaHistoryIntent.Back)
            },
            trailingContent = {
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = AppColors.green800
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .background(color = AppColors.white),
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.clear_history)) },
                            onClick = {
                                showMenu = false
                                showClearDialog = true
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = AppColors.gold500
                                )
                            }
                        )
                    }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            FilterTabs(
                selectedFilter = state.selectedFilter,
                onFilterSelected = { onIntent(MasbahaHistoryIntent.ChangeFilter(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SummaryCard(
                totalCount = state.totalTasbihCount,
                continuousDays = state.continuousDays,
                locale = Locale.Builder().setLanguage("ar").build()
            )

            Spacer(modifier = Modifier.height(24.dp))

            RecentActivityHeader(
                onViewAll = {
                    onIntent(MasbahaHistoryIntent.ViewAllRecentActivity)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(state.recentActivity) { activity ->
                    ActivityItem(activity)
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MasbahaHistoryScreenPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        MasbahaHistoryScreen(
            state = MasbahaHistoryState(
                totalTasbihCount = 1250,
                continuousDays = 7,
                recentActivity = listOf(
                    MasbahaHistoryEntity(
                        zekrText = "سبحان الله",
                        count = 33,
                        zekrId = 1,
                        timestamp = System.currentTimeMillis()
                    ),
                    MasbahaHistoryEntity(
                        zekrText = "الحمد لله",
                        count = 33,
                        zekrId = 2,
                        timestamp = System.currentTimeMillis() - 3600000
                    ),
                    MasbahaHistoryEntity(
                        zekrText = "الله أكبر",
                        count = 34,
                        zekrId = 3,
                        timestamp = System.currentTimeMillis() - 7200000
                    )
                )
            )
        )
    }
}
