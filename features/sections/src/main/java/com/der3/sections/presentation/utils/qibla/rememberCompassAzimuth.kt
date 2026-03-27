package com.der3.sections.presentation.utils.qibla


import android.content.Context
import android.hardware.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

// قيمة التنعيم لقراءات الحساس
private const val DEFAULT_SENSOR_SMOOTHING = 0.5f

/**
 * Composable يعيد زاوية اتجاه الهاتف بالنسبة للشمال (Azimuth)
 */
@Composable
fun rememberCompassAzimuth(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_GAME
): State<Float?> {

    val context = LocalContext.current

    // حالة لتخزين زاوية الاتجاه
    val azimuth = remember { mutableStateOf<Float?>(null) }

    DisposableEffect(context, sensorDelay) {

        val sensorManager = context.sensorManagerOrNull()
            ?: return@DisposableEffect onDispose {}

        // أفضل حساس لحساب الاتجاه
        val rotationSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        // حساسات احتياطية
        val accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val magnetometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        // إذا لم توجد الحساسات
        if (rotationSensor == null && (accelerometer == null || magnetometer == null)) {
            azimuth.value = null
            return@DisposableEffect onDispose {}
        }

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)

        val accelerometerReading = FloatArray(3)
        val magnetometerReading = FloatArray(3)

        var hasAccelerometer = false
        var hasMagnetometer = false

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                // استخدام Rotation Vector إذا كان متوفرًا
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {

                    SensorManager.getRotationMatrixFromVector(
                        rotationMatrix,
                        event.values
                    )

                    SensorManager.getOrientation(
                        rotationMatrix,
                        orientationAngles
                    )

                    val azimuthDegrees =
                        Math.toDegrees(orientationAngles[0].toDouble())
                            .toFloat()
                            .normalizeDegrees()

                    azimuth.value = azimuthDegrees
                    return
                }

                when (event.sensor.type) {

                    Sensor.TYPE_ACCELEROMETER -> {
                        event.values.copyIntoLowPass(accelerometerReading)
                        hasAccelerometer = true
                    }

                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        event.values.copyIntoLowPass(magnetometerReading)
                        hasMagnetometer = true
                    }
                }

                if (hasAccelerometer && hasMagnetometer) {

                    val success = SensorManager.getRotationMatrix(
                        rotationMatrix,
                        null,
                        accelerometerReading,
                        magnetometerReading
                    )

                    if (success) {

                        SensorManager.getOrientation(
                            rotationMatrix,
                            orientationAngles
                        )

                        val azimuthDegrees =
                            Math.toDegrees(orientationAngles[0].toDouble())
                                .toFloat()
                                .normalizeDegrees()

                        azimuth.value = azimuthDegrees
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit
        }

        // تسجيل الحساس
        if (rotationSensor != null) {

            sensorManager.registerListener(
                listener,
                rotationSensor,
                sensorDelay
            )

        } else {

            sensorManager.registerListener(listener, accelerometer, sensorDelay)
            sensorManager.registerListener(listener, magnetometer, sensorDelay)
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return azimuth
}

/**
 * الحصول على SensorManager
 */
private fun Context.sensorManagerOrNull(): SensorManager? =
    getSystemService(Context.SENSOR_SERVICE) as? SensorManager

/**
 * Low-pass filter لتنعيم بيانات الحساس
 */
private fun FloatArray.copyIntoLowPass(
    destination: FloatArray,
    alpha: Float = DEFAULT_SENSOR_SMOOTHING
) {
    for (index in indices) {
        destination[index] =
            alpha * destination[index] + (1f - alpha) * this[index]
    }
}