package com.der3.sections.presentation.prayer_times

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer_times.components.CalculationMethodDialog
import com.der3.sections.presentation.prayer_times.components.LocationSelectionDialog
import com.der3.sections.presentation.prayer_times.components.MonthlyCalendarDialog
import com.der3.sections.presentation.prayer_times.components.NextPrayerCard
import com.der3.sections.presentation.prayer_times.components.PrayerTimeItem
import com.der3.sections.presentation.prayer_times.mvi.*
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.R
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
    var showMethodDialog by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showCalendarDialog by remember { mutableStateOf(false) }

    if (showMethodDialog) {
        CalculationMethodDialog(
            state = state,
            onIntent = onIntent,
            onDismiss = { showMethodDialog = false }
        )
    }

    if (showLocationDialog) {
        LocationSelectionDialog(
            state = state,
            onIntent = onIntent,
            onDismiss = { showLocationDialog = false }
        )
    }

    if (showCalendarDialog) {
        MonthlyCalendarDialog(
            state = state,
            onDismiss = { showCalendarDialog = false }
        )
    }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { 
                        onIntent(PrayerTimeIntent.LoadMonthlyCalendar)
                        showCalendarDialog = true 
                    }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Calendar",
                            tint = AppColors.gray500
                        )
                    }
                    IconButton(onClick = { showLocationDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = AppColors.gray500
                        )
                    }
                    IconButton(onClick = { showMethodDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = AppColors.gray500
                        )
                    }
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
                if (prayer.type in listOf(PrayerType.FAJR, PrayerType.SUNRISE, PrayerType.DHUHR, PrayerType.ASR, PrayerType.MAGHRIB, PrayerType.ISHA)) {
                    PrayerTimeItem(
                        prayer = prayer,
                        onToggleNotification = { onIntent(PrayerTimeIntent.ToggleNotification(prayer.type)) }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        stringResource(R.string.other_times),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.green900,
                        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                    )
                    
                    val otherTimes = state.prayerTimes.filter { 
                        it.type !in listOf(PrayerType.FAJR, PrayerType.SUNRISE, PrayerType.DHUHR, PrayerType.ASR, PrayerType.MAGHRIB, PrayerType.ISHA) 
                    }
                    
                    otherTimes.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { other ->
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 6.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White,
                                    tonalElevation = 2.dp,
                                    shadowElevation = 1.dp
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = other.name,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AppColors.gray500,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = other.time,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = AppColors.green800
                                        )
                                    }
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
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
