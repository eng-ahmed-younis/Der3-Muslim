package com.der3.sections.presentation.prayer.location_picker

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Layers
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer.location_picker.components.CityCard
import com.der3.sections.presentation.prayer.location_picker.components.CurrentLocationCard
import com.der3.sections.presentation.prayer.location_picker.components.ManualMapPickerButton
import com.der3.sections.presentation.prayer.location_picker.components.MapPreviewCard
import com.der3.sections.presentation.prayer.location_picker.components.SearchBar
import com.der3.sections.presentation.prayer.location_picker.map_style.MapStyleBottomSheet
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerIntent
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerState
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.AppConfig
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LocationPickerRoute(
    onNavigate: (Screens) -> Unit
) {
    val viewModel = hiltViewModel<LocationPickerViewModel>()
    val state = viewModel.viewState
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (state.currentLat == 0.0 && state.currentLng == 0.0) {
            viewModel.onIntent(LocationPickerIntent.UseCurrentLocation)
        }
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                else -> {}
            }
        }.launchIn(scope)
    }

    LocationPickerScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun LocationPickerScreen(
    state: LocationPickerState,
    onIntent: (LocationPickerIntent) -> Unit
) {
    var showMapStyleSheet by remember { mutableStateOf(false) }

    LoadingDialog(visible = state.isLoading)

    if (showMapStyleSheet) {
        MapStyleBottomSheet(
            apiKey = AppConfig.GEOAPIFY_API_KEY,
            selectedStyle = state.mapStyle,
            onDismiss = { showMapStyleSheet = false },
            onStyleSelected = { onIntent(LocationPickerIntent.ChangeMapStyle(it)) }
        )
    }

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.location_selection_title),
            onBackClick = { onIntent(LocationPickerIntent.Back) },
            backgroundColor = AppColors.screenBackground,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            trailingContent = {
                IconButton(onClick = { showMapStyleSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = "Map Style",
                        tint = AppColors.gray900Text
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // Search Bar
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { onIntent(LocationPickerIntent.SearchCity(it)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Use Current Location Card
            CurrentLocationCard(
                lastUpdateTime = state.lastLocationUpdateTime,
                onClick = { onIntent(LocationPickerIntent.UseCurrentLocation) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Map Preview Card
            MapPreviewCard(
                lat = state.currentLat,
                lng = state.currentLng,
                mapStyle = state.mapStyle
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.home_view_all_action),
                    color = if (isDarkTheme) AppColors.gold500 else AppColors.green800,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* TODO */ }
                )
                Text(
                    text = stringResource(id = R.string.suggested_cities),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.gray900Text
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                state.suggestedCities.chunked(2).forEach { rowCities ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowCities.forEach { city ->
                            Box(modifier = Modifier.weight(1f)) {
                                CityCard(
                                    city = city,
                                    onClick = { onIntent(LocationPickerIntent.SelectCity(city.lat, city.lng, city.name)) }
                                )
                            }
                        }
                        if (rowCities.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Manual Selection Button
            ManualMapPickerButton(
                onClick = { onIntent(LocationPickerIntent.OpenManualMapPicker) }
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun LocationPickerScreenPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.LIGHT) {
        androidx.compose.runtime.CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LocationPickerScreen(
                state = LocationPickerState(
                    currentLat = 24.7136,
                    currentLng = 46.6753
                ),
                onIntent = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LocationPickerScreenDarkPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.DARK) {
        androidx.compose.runtime.CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LocationPickerScreen(
                state = LocationPickerState(
                    currentLat = 24.7136,
                    currentLng = 46.6753
                ),
                onIntent = {}
            )
        }
    }
}
