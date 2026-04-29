package com.der3.sections.presentation.prayer.location_picker.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.der3.model.GeographyMapStyle
import com.der3.utils.AppConfig
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point

private const val MARKER_SOURCE_ID = "marker-source"
private const val MARKER_LAYER_ID = "marker-layer"
private const val MARKER_IMAGE_ID = "marker-image"

@SuppressLint("ClickableViewAccessibility")
@Composable
fun GeoapifyMapView(
    modifier: Modifier = Modifier,
    latitude: Double? = null,
    longitude: Double? = null,
    mapStyle: GeographyMapStyle = GeographyMapStyle.OsmBright,
    showMarker: Boolean = true,
    geoapifyApiKey: String,
    context: Context,
    onCameraIdle: (Double, Double) -> Unit = { _, _ -> }
) {
    if (LocalInspectionMode.current) {
        Box(modifier = modifier.background(Color(0xFFD8E6CC)))
        return
    }

    // Must be called before any MapView is instantiated; safe to call repeatedly.
    MapLibre.getInstance(context)

    // MapView owns its own lifecycle state, so we create it once here and hand
    // the same instance to AndroidView.factory. Recreating it on every
    // recomposition would tear down and rebuild the GL surface unnecessarily.
    val mapView = remember {
        MapView(context).apply {
            onCreate(null)
        }
    }

    // Manual previous-value tracking because AndroidView.update fires on every
    // recomposition, not only when these inputs actually change.
    val prevLat   = remember { mutableStateOf(latitude) }
    val prevLng   = remember { mutableStateOf(longitude) }
    val prevStyle = remember { mutableStateOf(mapStyle) }

    // MapView has its own Android lifecycle that Compose doesn't drive
    // automatically, so we mirror it through DisposableEffect.
    DisposableEffect(Unit) {
        mapView.onStart()
        mapView.onResume()

        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    AndroidView(
        // pointerInput + awaitEachGesture: consumes all pointer events on the
        // Compose side so that parent scrollable containers don't steal the
        // map's drag/pan gestures.
        modifier = modifier.pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown(requireUnconsumed = false)
                do {
                    val event = awaitPointerEvent()
                    event.changes.forEach { it.consume() }
                } while (event.changes.any { it.pressed })
            }
        },
        factory = {
            mapView.apply {
                // requestDisallowInterceptTouchEvent prevents the Android view
                // hierarchy above the MapView from stealing touch events while
                // the user is scrolling or pinching the map.
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            v.parent.requestDisallowInterceptTouchEvent(true)
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            v.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    false
                }
                getMapAsync { map ->
                    map.uiSettings.isAttributionEnabled = false
                    map.uiSettings.isLogoEnabled = false
                    map.uiSettings.isCompassEnabled = true
                    map.uiSettings.isZoomGesturesEnabled = true
                    map.uiSettings.isScrollGesturesEnabled = true
                    map.uiSettings.isRotateGesturesEnabled = true
                    map.uiSettings.isTiltGesturesEnabled = true

                    map.addOnCameraIdleListener {
                        val center = map.cameraPosition.target
                        if (center != null) {
                            onCameraIdle(center.latitude, center.longitude)
                        }
                    }

                    // isInitial = true → snap camera instantly, no animation.
                    updateMap(
                        map = map,
                        mapStyle = mapStyle,
                        showMarker = showMarker,
                        geoapifyApiKey = geoapifyApiKey,
                        latitude = latitude,
                        longitude = longitude,
                        context = context,
                        mapView = mapView,
                        isInitial = true,
                    )
                }
            }
        },
        update = { view ->
            val latChanged   = prevLat.value   != latitude
            val lngChanged   = prevLng.value   != longitude
            val styleChanged = prevStyle.value != mapStyle

            if (latChanged || lngChanged || styleChanged) {
                prevLat.value   = latitude
                prevLng.value   = longitude
                prevStyle.value = mapStyle

                // isInitial = false → animate camera to the new position.
                view.getMapAsync { map ->
                    updateMap(
                        map = map,
                        mapStyle = mapStyle,
                        showMarker = showMarker,
                        geoapifyApiKey = geoapifyApiKey,
                        latitude = latitude,
                        longitude = longitude,
                        context = context,
                        mapView = view,
                        isInitial = false,
                    )
                }
            }
        },
    )
}

private fun updateMap(
    map: MapLibreMap,
    mapStyle: GeographyMapStyle,
    showMarker: Boolean,
    geoapifyApiKey: String,
    latitude: Double?,
    longitude: Double?,
    context: Context,
    mapView: MapView,
    isInitial: Boolean,
) {
    val styleUrl     = mapStyle.styleUrl(geoapifyApiKey)
    val currentStyle = map.style

    // Only reload the style when the URL actually changed; setStyle tears down
    // and rebuilds all layers, so calling it unnecessarily causes a visible flash.
    if (currentStyle == null || currentStyle.uri != styleUrl) {
        map.setStyle(styleUrl) { style ->
            if (showMarker) ensureMarkerImage(style, context)
            updateLocationAndMarker(map, style, showMarker, latitude, longitude, context, mapView, isInitial)
        }
    } else {
        updateLocationAndMarker(map, currentStyle, showMarker, latitude, longitude, context, mapView, isInitial)
    }
}

// Adds the default marker bitmap to the style only once per style load.
private fun ensureMarkerImage(style: Style, context: Context) {
    if (style.getImage(MARKER_IMAGE_ID) == null) {
        ContextCompat.getDrawable(context, org.maplibre.android.R.drawable.maplibre_marker_icon_default)?.let {
            style.addImage(MARKER_IMAGE_ID, it)
        }
    }
}

private fun updateLocationAndMarker(
    map: MapLibreMap,
    style: Style,
    showMarker: Boolean,
    latitude: Double?,
    longitude: Double?,
    context: Context,
    mapView: MapView,
    isInitial: Boolean,
) {
    // (0.0, 0.0) is "Null Island" and in this app signals an uninitialised
    // location value, so we treat it the same as null.
    if (latitude != null && longitude != null && (latitude != 0.0 || longitude != 0.0)) {
        val pos = LatLng(latitude, longitude)

        if (isInitial) {
            // First load — position the camera instantly so there is no
            // animated fly-in from the default (0, 0) position.
            map.cameraPosition = CameraPosition.Builder()
                .target(pos)
                .zoom(14.0)
                .build()
        } else {
            val currentTarget = map.cameraPosition.target

            // Sub-metre tolerance prevents re-animating the camera when the
            // coordinate difference is only floating-point rounding noise.
            val alreadyThere = currentTarget != null &&
                kotlin.math.abs(currentTarget.latitude  - latitude)  < 0.00005 &&
                kotlin.math.abs(currentTarget.longitude - longitude) < 0.00005

            if (!alreadyThere) {
                // Keep the user's current zoom level; fall back to 14 only if
                // the map was never meaningfully zoomed in (< 5 means world view).
                val currentZoom = map.cameraPosition.zoom
                val targetZoom  = if (currentZoom < 5.0) 14.0 else currentZoom
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, targetZoom), 1000)
            }
        }

        if (showMarker) {
            // Re-check after a style reload because all images are wiped.
            ensureMarkerImage(style, context)

            val source  = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)
            val feature = Feature.fromGeometry(Point.fromLngLat(longitude, latitude))

            if (source == null) {
                style.addSource(GeoJsonSource(MARKER_SOURCE_ID, feature))
            } else {
                source.setGeoJson(feature)
            }

            if (style.getLayer(MARKER_LAYER_ID) == null) {
                val layer = SymbolLayer(MARKER_LAYER_ID, MARKER_SOURCE_ID)
                    .withProperties(
                        PropertyFactory.iconImage(MARKER_IMAGE_ID),
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true)
                    )
                style.addLayer(layer)
            }
        }
    } else if (isInitial) {
        // No coordinates on first load — centre on the device's current
        // position as a sensible default instead of showing Null Island.
        moveMapToCurrentLocation(
            context    = context,
            showMarker = showMarker,
            mapView    = mapView
        )
    }
}

@SuppressLint("MissingPermission")
private fun moveMapToCurrentLocation(
    context: Context,
    showMarker: Boolean,
    mapView: MapView,
) {
    val hasFinePermission =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    val hasCoarsePermission =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    if (!hasFinePermission && !hasCoarsePermission) return

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // CancellationTokenSource is not stored because this is a fire-and-forget
    // request; the map is already being destroyed if the user leaves the screen.
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location: Location? ->

        if (location == null) return@addOnSuccessListener

        val currentLatLng = LatLng(location.latitude, location.longitude)

        mapView.getMapAsync { map ->
            // Keep zoom if the user already zoomed in; 15 is street level.
            val currentZoom = map.cameraPosition.zoom
            val targetZoom  = if (currentZoom < 10.0) 15.0 else currentZoom
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, targetZoom), 1000)

            if (showMarker) {
                map.getStyle { style ->
                    ensureMarkerImage(style, context)

                    val source  = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)
                    val feature = Feature.fromGeometry(Point.fromLngLat(location.longitude, location.latitude))

                    if (source == null) {
                        style.addSource(GeoJsonSource(MARKER_SOURCE_ID, feature))
                    } else {
                        source.setGeoJson(feature)
                    }

                    if (style.getLayer(MARKER_LAYER_ID) == null) {
                        val layer = SymbolLayer(MARKER_LAYER_ID, MARKER_SOURCE_ID)
                            .withProperties(
                                PropertyFactory.iconImage(MARKER_IMAGE_ID),
                                PropertyFactory.iconAllowOverlap(true),
                                PropertyFactory.iconIgnorePlacement(true)
                            )
                        style.addLayer(layer)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeoapifyMapViewPreview() {
    GeoapifyMapView(
        modifier        = Modifier.fillMaxSize(),
        geoapifyApiKey  = AppConfig.GEOAPIFY_API_KEY,
        context         = LocalContext.current
    )
}
