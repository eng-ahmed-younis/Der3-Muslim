package com.der3.sections.presentation.prayer.prayer_setting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.model.AppStyle
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer.prayer_setting.components.CalculationMethodItem
import com.der3.sections.presentation.prayer.prayer_setting.components.HighLatitudeSection
import com.der3.sections.presentation.prayer.prayer_setting.components.LocationCard
import com.der3.sections.presentation.prayer.prayer_setting.components.MadhabSection
import com.der3.sections.presentation.prayer.prayer_setting.components.ManualAdjustmentSection
import com.der3.sections.presentation.prayer.prayer_setting.components.SectionHeader
import com.der3.sections.presentation.prayer.prayer_setting.mvi.CalculationMethodUi
import com.der3.sections.presentation.prayer.prayer_setting.mvi.Madhab
import com.der3.sections.presentation.prayer.prayer_setting.mvi.PrayerSettingIntent
import com.der3.sections.presentation.prayer.prayer_setting.mvi.PrayerSettingState
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun PrayerSettingRoute(
    onNavigate: (Screens) -> Unit = {}
){

    val viewModel = hiltViewModel<PrayerSettingViewModel>()
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
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            viewModel.onIntent(PrayerSettingIntent.Retry)
            showErrorDialog = false
            errorMessage = null
        },
        onDismiss = {
            showErrorDialog = false
            errorMessage = null
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )


    PrayerSettingScreen(
        state = state,
        onIntent = { viewModel.onIntent(it) }
    )
}
@Composable
fun PrayerSettingScreen(
    state: PrayerSettingState,
    onIntent: (PrayerSettingIntent) -> Unit = {}
) {
    LoadingDialog(visible = state.isLoading || state.isSaving)

    val headerContentColor = if (isDarkTheme) AppColors.gray900Text else AppColors.gray900Text

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
    ) {
        Der3TopAppBar(
            title = stringResource(id = com.der3.ui.R.string.prayer_settings_title),
            onBackClick = { onIntent(PrayerSettingIntent.Back) },
            backgroundColor = AppColors.screenBackground,
            titleColor = AppColors.gray900Text,
            navigationIconColor = AppColors.gray900Text
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Location Section
            item {
                SectionHeader(title = stringResource(id = com.der3.ui.R.string.location_label))
                Spacer(modifier = Modifier.height(8.dp))
                LocationCard(
                    locationName = state.locationName,
                    onChangeClick = { onIntent(PrayerSettingIntent.ChangeLocation) }
                )
            }

            // Calculation Method Section
            item {
                SectionHeader(title = stringResource(id = com.der3.ui.R.string.calculation_method))
                Spacer(modifier = Modifier.height(8.dp))
            }

            val methods = listOf(
                CalculationMethodUi(3, ""), // ID 3: Muslim World League
                CalculationMethodUi(4, ""), // ID 4: Umm Al-Qura
                CalculationMethodUi(5, "")  // ID 5: Egyptian General Authority
            )

            items(methods) { method ->
                val methodName = when(method.id) {
                    3 -> stringResource(id = com.der3.ui.R.string.calculation_method_3)
                    4 -> stringResource(id = com.der3.ui.R.string.calculation_method_4)
                    5 -> stringResource(id = com.der3.ui.R.string.calculation_method_5)
                    else -> ""
                }
                CalculationMethodItem(
                    method = method.copy(name = methodName),
                    isSelected = state.selectedMethodId == method.id,
                    onSelect = { onIntent(PrayerSettingIntent.SelectCalculationMethod(method.id)) }
                )
            }

            // Madhab Section
            item {
                MadhabSection(
                    selectedMadhab = state.selectedMadhab,
                    onMadhabSelect = { onIntent(PrayerSettingIntent.SelectMadhab(it)) }
                )
            }

            // High Latitude Adjustment Section
            item {
                HighLatitudeSection(
                    selectedMethod = state.highLatitudeAdjustment,
                    onMethodSelect = { onIntent(PrayerSettingIntent.SelectHighLatitudeMethod(it)) }
                )
            }

            // Manual Adjustment Section
            item {
                ManualAdjustmentSection(
                    offsets = state.manualOffsets,
                    onOffsetChange = { prayer, offset ->
                        onIntent(PrayerSettingIntent.UpdateManualAdjustment(prayer, offset))
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }

        Button(
            onClick = { onIntent(PrayerSettingIntent.SaveSettings) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.gold500),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = com.der3.ui.R.drawable.save),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = AppColors.green900
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = com.der3.ui.R.string.save_changes),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.green900
                )
            }
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun PrayerSettingScreenLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        PrayerSettingScreen(
            state = PrayerSettingState(
                locationName = "الرياض، المملكة العربية السعودية",
                selectedMethodId = 2,
                selectedMadhab = Madhab.SHAFI
            ),
            onIntent = {}
        )
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PrayerSettingScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        PrayerSettingScreen(
            state = PrayerSettingState(
                locationName = "الرياض، المملكة العربية السعودية",
                selectedMethodId = 2,
                selectedMadhab = Madhab.SHAFI
            ),
            onIntent = {}
        )
    }
}
