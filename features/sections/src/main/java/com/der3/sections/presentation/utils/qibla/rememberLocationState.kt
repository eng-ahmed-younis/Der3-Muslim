package com.der3.sections.presentation.utils.qibla


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

// الحد الأدنى للوقت بين تحديثات الموقع (1 ثانية)
private const val DEFAULT_LOCATION_MIN_TIME = 1_000L

// الحد الأدنى للمسافة بين التحديثات (1 متر)
private const val DEFAULT_LOCATION_MIN_DISTANCE = 1f

/**
 * Composable يقوم بالاشتراك في تحديثات الموقع
 * ويعيد الموقع الحالي كـ State
 */
@SuppressLint("MissingPermission")
@Composable
fun rememberLocationState(
    hasLocationPermission: Boolean,

    // أقل وقت بين تحديثات الموقع
    minTimeBetweenUpdatesMillis: Long = DEFAULT_LOCATION_MIN_TIME,

    // أقل مسافة بين التحديثات
    minDistanceBetweenUpdatesMeters: Float = DEFAULT_LOCATION_MIN_DISTANCE,

    // مزودو الموقع
    locationProviders: List<String> = listOf(
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER
    )
): State<Location?> {

    // الحصول على Context
    val context = LocalContext.current

    // حالة لتخزين الموقع
    val locationState = remember { mutableStateOf<Location?>(null) }

    DisposableEffect(
        context,
        hasLocationPermission,
        minTimeBetweenUpdatesMillis,
        minDistanceBetweenUpdatesMeters,
        locationProviders
    ) {

        // إذا لم يكن هناك إذن موقع
        if (!hasLocationPermission) {
            locationState.value = null
            return@DisposableEffect onDispose {}
        }

        // الحصول على LocationManager
        val locationManager = context.locationManagerOrNull()
            ?: return@DisposableEffect onDispose {}

        // مستمع تحديثات الموقع
        val listener = object : LocationListener {

            // عند تغير الموقع
            override fun onLocationChanged(location: Location) {
                locationState.value = location
            }

            override fun onProviderEnabled(provider: String) = Unit

            override fun onProviderDisabled(provider: String) = Unit

            @Deprecated("Deprecated in Android 13")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
        }

        // تحديد الـLooper (الخيط)
        val looper = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.mainLooper
        } else {
            Looper.getMainLooper()
        }

        // التحقق من المزودين المفعلين
        val registeredProviders = locationProviders.filter {
            locationManager.isProviderEnabled(it)
        }

        // إذا لم يوجد مزود فعال
        if (registeredProviders.isEmpty()) {

            // محاولة الحصول على آخر موقع معروف
            locationState.value =
                locationManager.bestLastKnownLocation(locationProviders)

            return@DisposableEffect onDispose {}
        }

        // تعيين آخر موقع معروف
        locationState.value =
            locationManager.bestLastKnownLocation(registeredProviders)

        // طلب تحديثات الموقع
        val registrationResult = runCatching {

            registeredProviders.forEach { provider ->
                locationManager.requestLocationUpdates(
                    provider,
                    0L,
                    0f,
                    listener,
                    looper
                )
            }
        }

        // في حال فشل الاشتراك
        if (registrationResult.isFailure) {

            locationState.value =
                locationManager.bestLastKnownLocation(locationProviders)

            return@DisposableEffect onDispose {}
        }

        // عند إزالة الـComposable
        onDispose {

            // إلغاء تحديثات الموقع
            locationManager.removeUpdates(listener)
        }
    }

    return locationState
}

/**
 * التحقق من وجود إذن الموقع
 */
fun Context.hasLocationPermission(): Boolean {

    val fineGranted = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coarseGranted = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    return fineGranted || coarseGranted
}

/**
 * اختيار أفضل موقع معروف من جميع المزودين
 */
@RequiresPermission(anyOf = [
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
])
private fun LocationManager.bestLastKnownLocation(
    providers: List<String>
): Location? {

    var bestLocation: Location? = null

    for (provider in providers) {

        runCatching { getLastKnownLocation(provider) }
            .getOrNull()
            ?.let { location ->

                if (bestLocation == null ||
                    location.time > (bestLocation?.time ?: 0L)
                ) {
                    bestLocation = location
                }
            }
    }

    return bestLocation
}

/**
 * الحصول على LocationManager من النظام
 */
private fun Context.locationManagerOrNull(): LocationManager? =
    getSystemService(Context.LOCATION_SERVICE) as? LocationManager