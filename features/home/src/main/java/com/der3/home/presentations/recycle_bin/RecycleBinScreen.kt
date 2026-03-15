package com.der3.home.presentations.recycle_bin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.domain.model.DeletedZekrUiModel
import com.der3.home.domain.model.ZekrUiModel
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinIntent
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinState
import com.der3.home.presentations.recycle_bin.components.DeletedZekrCard
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

@Composable
fun RecycleBinRoute(
    onNavigate: (Screens) -> Unit
) {
    val viewModel: RecycleBinViewModel = hiltViewModel()
    val state = viewModel.viewState
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach { effect ->
            when (effect) {
                is MviEffect.Navigate -> {
                    onNavigate(effect.screen)
                }
                is MviEffect.OnErrorDialog -> {
                    errorMessage = effect.error.asString(context)
                    showErrorDialog = true
                }
                else -> {}
            }
        }.launchIn(scope)
    }

    LoadingDialog(visible = state.isLoading)

    ErrorDialog(
        visible = showErrorDialog || state.error != null,
        message = errorMessage ?: state.error,
        onRetry = {
            viewModel.onIntent(RecycleBinIntent.Retry)
            showErrorDialog = false
            errorMessage = null
        },
        onDismiss = {
            viewModel.onIntent(RecycleBinIntent.DismissError)
            showErrorDialog = false
            errorMessage = null
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.white,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

    RecycleBinScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@Composable
fun RecycleBinScreen(
    state: RecycleBinState,
    onIntent: (RecycleBinIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.white)
    ) {
        Der3TopAppBar(
            title = stringResource(R.string.recycle_bin_title),
            onBackClick = {
                onIntent(RecycleBinIntent.Back)
            },
            showBackButton = true,
            backgroundColor = AppColors.white,
            titleColor = AppColors.gray900Text,
            navigationIconColor = AppColors.gray900Text,
            trailingContent = {
                IconButton(onClick = { /* Handle more */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = AppColors.gray900Text,
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults
                    .cardColors(
                        containerColor = AppColors.gold600.copy(alpha = 0.4f)
                    ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    ),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = AppColors.gold700,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.recycle_bin_info),
                        color = AppColors.gray900Text,
                        fontSize = 14.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.items.isEmpty() && !state.isLoading) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = AppColors.gold700,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.recycle_bin_empty),
                            color = AppColors.gold700,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.items, key = { it.zekr.id }) { item ->
                        DeletedZekrCard(
                            item = item,
                            onRestore = { onIntent(RecycleBinIntent.RestoreItem(item.zekr.id)) },
                            onDeletePermanently = { onIntent(RecycleBinIntent.DeletePermanently(item.zekr.id)) }
                        )
                    }
                }
            }

            // Bottom Button
            if (state.items.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { onIntent(RecycleBinIntent.EmptyBin) },
                        colors = ButtonDefaults.buttonColors(containerColor  = AppColors.red50),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = AppColors.red900.copy(alpha = 0.9f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.empty_recycle_bin),
                                color = AppColors.red900.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecycleBinScreenPreview() {
    Der3MuslimTheme(
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        RecycleBinScreen(
            state = RecycleBinState(
               /* items = listOf(
                    DeletedZekrUiModel(
                        zekr = ZekrUiModel(
                            id = 1,
                            text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ، وَالحَمْدُ للهِ",
                            categoryName = "أذكار الصباح",
                            repeatCount = 1,
                            audioPath = ""
                        ),
                        deletedSince = "يومين"
                    )
                )*/
            ),
            onIntent = {}
        )
    }
}
