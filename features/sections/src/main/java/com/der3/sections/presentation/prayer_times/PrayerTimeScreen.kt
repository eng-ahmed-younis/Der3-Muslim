package com.der3.sections.presentation.prayer_times

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer_times.components.NextPrayerCard
import com.der3.sections.presentation.prayer_times.components.PrayerTimeItem
import com.der3.sections.presentation.prayer_times.mvi.*
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun PrayerTimeRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel = hiltViewModel<PrayerTimeViewModel>()
    val state = viewModel.viewState

    LoadingDialog(visible = state.isLoading)

    ErrorDialog(
        visible = state.error != null,
        message = state.error,
        onRetry = { viewModel.onIntent(PrayerTimeIntent.LoadPrayerTimes) },
        onDismiss = { viewModel.onIntent(PrayerTimeIntent.DismissError) }
    )

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                else -> {}
            }
        }.launchIn(this)
    }

    PrayerTimeScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun PrayerTimeScreen(
    state: PrayerTimeState,
    onIntent: (PrayerTimeIntent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {
        Der3TopAppBar(
            title = "أوقات الصلاة",
            subtitle = state.locationName,
            backgroundColor = Color.Transparent,
            showBackButton = true,
            onBackClick = { onIntent(PrayerTimeIntent.Back) },
            trailingContent = {
                IconButton(onClick = { onIntent(PrayerTimeIntent.OpenQibla) }) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AppColors.gold500.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = com.der3.ui.R.drawable.ic_qibla),
                            contentDescription = "Qibla",
                            tint = AppColors.gold600,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        state.hijriDate,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.green900
                    )
                    Text(
                        state.gregorianDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.gray500
                    )
                }
            }

            item {
                state.nextPrayer?.let { next ->
                    NextPrayerCard(next)
                }
            }

            items(state.prayerTimes) { prayer ->
                PrayerTimeItem(
                    prayer = prayer,
                    onToggleNotification = { onIntent(PrayerTimeIntent.ToggleNotification(prayer.type)) }
                )
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrayerTimeScreenPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        PrayerTimeScreen(
            state = PrayerTimeState(
                locationName = "الرياض، المملكة العربية السعودية",
                hijriDate = "الأربعاء 15 رمضان 1445 هـ",
                gregorianDate = "27 مارس 2024",
                nextPrayer = PrayerDetails("العصر", "03:45 م", isNext = true, type = PrayerType.ASR),
                prayerTimes = listOf(
                    PrayerDetails("الفجر", "04:42 ص", isPassed = true, type = PrayerType.FAJR),
                    PrayerDetails("الشروق", "06:01 ص", isPassed = true, type = PrayerType.SUNRISE),
                    PrayerDetails("الظهر", "12:05 م", isPassed = true, type = PrayerType.DHUHR),
                    PrayerDetails("العصر", "03:45 م", isNext = true, type = PrayerType.ASR),
                    PrayerDetails("المغرب", "06:12 م", type = PrayerType.MAGHRIB),
                    PrayerDetails("العشاء", "07:42 م", type = PrayerType.ISHA)
                )
            )
        )
    }
}
