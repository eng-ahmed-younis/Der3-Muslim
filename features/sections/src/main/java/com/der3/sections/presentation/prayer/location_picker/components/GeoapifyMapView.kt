package com.der3.sections.presentation.prayer.location_picker.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun GeoapifyMapView(
    modifier: Modifier = Modifier,
    latitude: Double? = null,
    longitude: Double? = null,
    mapStyle: GeographyMapStyle = GeographyMapStyle.OsmBright,
    geoapifyApiKey: String,
    context: Context,
) {
    MapLibre.getInstance(context)

    val mapView = remember {
        MapView(context).apply {
            onCreate(null)
        }
    }

    val prevLat = remember { mutableStateOf(latitude) }
    val prevLng = remember { mutableStateOf(longitude) }
    val prevStyle = remember { mutableStateOf(mapStyle) }

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
        modifier = modifier,
        factory = {
            mapView.apply {
                getMapAsync { map ->
                    map.uiSettings.isAttributionEnabled = false
                    map.uiSettings.isLogoEnabled = false

                    updateMap(
                        map = map,
                        mapStyle = mapStyle,
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
            val latChanged = prevLat.value != latitude
            val lngChanged = prevLng.value != longitude
            val styleChanged = prevStyle.value != mapStyle

            if (latChanged || lngChanged || styleChanged) {
                prevLat.value = latitude
                prevLng.value = longitude
                prevStyle.value = mapStyle

                view.getMapAsync { map ->
                    updateMap(
                        map = map,
                        mapStyle = mapStyle,
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
    geoapifyApiKey: String,
    latitude: Double?,
    longitude: Double?,
    context: Context,
    mapView: MapView,
    isInitial: Boolean,
) {
    val styleUrl = mapStyle.styleUrl(geoapifyApiKey)
    val currentStyle = map.style

    if (currentStyle == null || (currentStyle.uri != styleUrl)) {
        map.setStyle(styleUrl) { style ->
            // Add default marker icon to style
            ContextCompat.getDrawable(context, org.maplibre.android.R.drawable.maplibre_marker_icon_default)?.let {
                style.addImage(MARKER_IMAGE_ID, it)
            }
            updateLocationAndMarker(map, style, latitude, longitude, context, mapView, isInitial)
        }
    } else {
        updateLocationAndMarker(map, currentStyle, latitude, longitude, context, mapView, isInitial)
    }
}

private fun updateLocationAndMarker(
    map: MapLibreMap,
    style: Style,
    latitude: Double?,
    longitude: Double?,
    context: Context,
    mapView: MapView,
    isInitial: Boolean,
) {
    // If coordinates are valid (and not 0.0 which usually means uninitialized in this app's state)
    if (latitude != null && longitude != null && (latitude != 0.0 || longitude != 0.0)) {
        val pos = LatLng(latitude, longitude)
        
        // Move camera
        if (isInitial) {
            map.cameraPosition = CameraPosition.Builder()
                .target(pos)
                .zoom(8.0)
                .build()
        } else {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 12.0))
        }

        // Update Source
        val source = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)
        val feature = Feature.fromGeometry(Point.fromLngLat(longitude, latitude))
        
        if (source == null) {
            style.addSource(GeoJsonSource(MARKER_SOURCE_ID, feature))
            val layer = SymbolLayer(MARKER_LAYER_ID, MARKER_SOURCE_ID)
                .withProperties(PropertyFactory.iconImage(MARKER_IMAGE_ID))
            style.addLayer(layer)
        } else {
            source.setGeoJson(feature)
        }
    } else if (isInitial) {
        // Only fetch current location automatically on initial load if no coordinates provided
        moveMapToCurrentLocation(
            context = context,
            mapView = mapView
        )
    }
}

@SuppressLint("MissingPermission")
private fun moveMapToCurrentLocation(
    context: Context,
    mapView: MapView,
) {
    val hasFinePermission =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    val hasCoarsePermission =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    if (!hasFinePermission && !hasCoarsePermission) return

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location: Location? ->

        if (location == null) return@addOnSuccessListener

        val currentLatLng = LatLng(
            location.latitude,
            location.longitude
        )

        mapView.getMapAsync { map ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0))
            
            val style = map.style
            if (style != null && style.isFullyLoaded) {
                val source = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)
                val feature = Feature.fromGeometry(Point.fromLngLat(location.longitude, location.latitude))
                
                if (source == null) {
                    style.addSource(GeoJsonSource(MARKER_SOURCE_ID, feature))
                    val layer = SymbolLayer(MARKER_LAYER_ID, MARKER_SOURCE_ID)
                        .withProperties(PropertyFactory.iconImage(MARKER_IMAGE_ID))
                    style.addLayer(layer)
                } else {
                    source.setGeoJson(feature)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeoapifyMapViewPreview() {
    GeoapifyMapView(
        modifier = Modifier.fillMaxSize(),
        geoapifyApiKey = AppConfig.GEOAPIFY_API_KEY,
        context = LocalContext.current
    )
}
