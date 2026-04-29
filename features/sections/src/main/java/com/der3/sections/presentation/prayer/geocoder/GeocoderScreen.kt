package com.der3.sections.presentation.prayer.geocoder

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer.geocoder.component.BottomLocationCard
import com.der3.sections.presentation.prayer.geocoder.component.GeocoderControls
import com.der3.sections.presentation.prayer.geocoder.component.GeocoderMarker
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderIntent
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderState
import com.der3.sections.presentation.prayer.location_picker.components.GeoapifyMapView
import com.der3.sections.presentation.prayer.location_picker.map_style.MapStyleBottomSheet
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.utils.AppConfig
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun GeocoderRoute(
    onNavigate: (Screens) -> Unit
) {
    val viewModel = hiltViewModel<GeocoderViewModel>()
    val state = viewModel.viewState
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = remember { context as? ComponentActivity }

    SideEffect {
        activity?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                else -> {}
            }
        }.launchIn(scope)
    }
    ShiftSystemBarStyle(
        navigationBarColor = AppColors.white,
        isStatusBarTransparent = true,
        useDarkStatusBarIcons = true,
        useDarkNavigationBarIcons = true,
        isStatusBarVisible = true,
        isNavigationBarVisible = false,
        isEdgeToEdgeEnabled = false
    )


    GeocoderScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun GeocoderScreen(
    state: GeocoderState,
    onIntent: (GeocoderIntent) -> Unit
) {
    val context = LocalContext.current
    var showMapStyleSheet by remember { mutableStateOf(false) }

    LoadingDialog(visible = state.isLoading)

    if (showMapStyleSheet) {
        MapStyleBottomSheet(
            apiKey = AppConfig.GEOAPIFY_API_KEY,
            selectedStyle = state.mapStyle,
            onDismiss = { showMapStyleSheet = false },
            onStyleSelected = { onIntent(GeocoderIntent.ChangeMapStyle(it)) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Transparent
            )
    ) {
        // Map in background
        GeoapifyMapView(
            modifier = Modifier.fillMaxSize(),
            latitude = state.latitude,
            longitude = state.longitude,
            mapStyle = state.mapStyle,
            showMarker = false,
            geoapifyApiKey = AppConfig.GEOAPIFY_API_KEY,
            context = context,
            onCameraIdle = { lat, lng ->
                onIntent(GeocoderIntent.MapCameraMoved(lat, lng))
            }
        )

        // Custom Marker in center (Fixed on screen)
        GeocoderMarker(modifier = Modifier.align(Alignment.Center))

        // Top Bar and My Location
        GeocoderControls(
            onBackClick = { onIntent(GeocoderIntent.Back) },
            onMyLocationClick = { onIntent(GeocoderIntent.UseCurrentLocation) },
            onMapStyleClick = { showMapStyleSheet = true }
        )

        // Bottom Card
        BottomLocationCard(
            modifier = Modifier.align(Alignment.BottomCenter),
            address = state.address,
            latitude = state.latitude,
            longitude = state.longitude,
            onConfirmClick = { onIntent(GeocoderIntent.ConfirmLocation) }
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GeocoderScreenLightPreview() {
    val dummyState = GeocoderState(
        latitude = 21.4131,
        longitude = 39.8264,
        address = "شارع إبراهيم الخليل، مكة المكرمة",
        mapStyle = com.der3.model.GeographyMapStyle.OsmBright,
        isLoading = false
    )
    com.der3.ui.themes.Der3MuslimTheme(style = com.der3.model.AppStyle.LIGHT) {
        GeocoderScreen(
            state = dummyState,
            onIntent = {}
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GeocoderScreenDarkPreview() {
    val dummyState = GeocoderState(
        latitude = 21.4131,
        longitude = 39.8264,
        address = "شارع إبراهيم الخليل، مكة المكرمة",
        mapStyle = com.der3.model.GeographyMapStyle.DarkMatter,
        isLoading = false
    )
    com.der3.ui.themes.Der3MuslimTheme(style = com.der3.model.AppStyle.DARK) {
        GeocoderScreen(
            state = dummyState,
            onIntent = {}
        )
    }
}

