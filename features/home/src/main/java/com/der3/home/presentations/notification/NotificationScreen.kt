package com.der3.home.presentations.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.der3.ui.R
import com.der3.home.domain.model.NotificationItem
import com.der3.home.domain.model.NotificationType
import com.der3.home.presentations.notification.components.AyaCard
import com.der3.home.presentations.notification.components.EmptyNotificationsState
import com.der3.home.presentations.notification.components.NotificationCard
import com.der3.home.presentations.notification.components.SectionHeader
import com.der3.home.presentations.notification.mvi.NotificationIntent
import com.der3.home.presentations.notification.mvi.NotificationState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.der3.model.AppStyle
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import com.der3.home.di.factory.MasbahaViewModelFactory
import com.der3.home.di.factory.NotificationViewModelFactory
import com.der3.home.presentations.masbaha.MasbahaViewModel
import com.der3.shared.params.NotificationParams
import java.util.Locale

@Composable
fun NotificationRoute(
    params: NotificationParams,
    onNavigate: (Screens) -> Unit
) {

    val viewModel: NotificationViewModel =
        hiltViewModel<NotificationViewModel, NotificationViewModelFactory> { factory ->
            factory.create(params)
        }

    val state = viewModel.viewState

    val scope = rememberCoroutineScope()
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
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(NotificationIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(NotificationIntent.DismissError)
        }
    )
    LoadingDialog(visible = state.isLoading)

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.screenBackground,
        useDarkNavigationBarIcons = false
    )

    NotificationScreen(
        state = state,
        onIntent = viewModel::onIntent
    )


}

@Composable
fun NotificationScreen(
    state: NotificationState,
    onIntent: (NotificationIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.screenBackground)
    ) {
        Der3TopAppBar(
            title = stringResource(R.string.notification_history_title),
            backgroundColor = AppColors.screenBackground,
            onBackClick = {
                onIntent(NotificationIntent.Back)
            },
            showBackButton = true
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = AppColors.green800
                )
            } else if (state.error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.notification_error_title),
                        color = AppColors.red900,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.error,
                        textAlign = TextAlign.Center,
                        color = AppColors.gray500
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onIntent(NotificationIntent.Retry) },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.green800)
                    ) {
                        Text(
                            text = stringResource(R.string.notification_retry_button),
                            color = Color.White
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Section: Today
                    if (state.todayNotifications.isNotEmpty()) {
                        item {
                            SectionHeader(title = stringResource(R.string.notification_today))
                        }

                        items(state.todayNotifications) { notification ->
                            NotificationCard(notification = notification)
                        }
                    }

                    // Section: Yesterday
                    if (state.yesterdayNotifications.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            SectionHeader(title = stringResource(R.string.notification_yesterday))
                        }

                        items(state.yesterdayNotifications) { notification ->
                            NotificationCard(notification = notification)
                        }
                    }

                    // Section: Aya of the Day
                    state.ayaOfTheDay?.let { aya ->
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                            AyaCard(aya = aya)
                        }
                    }

                    // Empty State Footer if everything is empty
                    if (state.todayNotifications.isEmpty() && state.yesterdayNotifications.isEmpty() && state.ayaOfTheDay == null) {
                        item {
                            EmptyNotificationsState()
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.notification_empty_state),
                                    color = AppColors.gray900Text,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Preview(showBackground = true, locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotificationScreenPreview() {
    val mockState = NotificationState(
        todayNotifications = listOf(
            NotificationItem(
                id = "1",
                title = "حان موعد صلاة العصر",
                description = "الصلاة خير من النوم، أقم صلاتك تنعم بحياتك.",
                time = "الآن",
                type = NotificationType.DAILY
            ),
            NotificationItem(
                id = "2",
                title = "أذكار المساء",
                description = "لا تنسى وردك من أذكار المساء لتحصين نفسك.",
                time = "منذ ساعتين",
                type = NotificationType.DAILY
            ),
            NotificationItem(
                id = "3",
                title = "تحديث جديد متوفر",
                description = "تم إضافة ميزات جديدة في المسبحة الإلكترونية، استكشفها الآن.",
                time = "منذ ٤ ساعات",
                type = NotificationType.DAILY
            )
        ),
        yesterdayNotifications = emptyList(),
        ayaOfTheDay = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ"
    )

    Der3MuslimTheme(
        style = if (isSystemInDarkTheme()) AppStyle.DARK else AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        NotificationScreen(state = mockState) {}
    }
}
