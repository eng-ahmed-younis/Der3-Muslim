package com.der3.sections.presentation.utils.qibla

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

// إحداثيات الكعبة المشرفة في مكة
private const val KAABA_LATITUDE = 21.422487
private const val KAABA_LONGITUDE = 39.826206

/**
 * تحسب اتجاه القبلة من موقع المستخدم.
 *
 * @param latitude خط العرض للمستخدم
 * @param longitude خط الطول للمستخدم
 *
 * @return زاوية الاتجاه بالدرجات
 * حيث:
 * 0° = الشمال
 * 90° = الشرق
 * 180° = الجنوب
 * 270° = الغرب
 */
/**
 * تحسب اتجاه القبلة من موقع المستخدم باستخدام معادلات حساب المثلثات الكروية.
 *
 * المبدأ: بما أن الأرض كروية، نستخدم مسار "الدائرة العظمى" (Great Circle)
 * وهو أقصر مسار بين نقطتين على سطح الكرة.
 */
fun calculateQiblaDirection(latitude: Double, longitude: Double): Float {

    // تحويل الإحداثيات من درجات إلى راديان
    val kaabaLatRad = Math.toRadians(KAABA_LATITUDE)
    val kaabaLngRad = Math.toRadians(KAABA_LONGITUDE)

    val userLatRad = Math.toRadians(latitude)
    val userLngRad = Math.toRadians(longitude)

    // الفرق بين خطوط الطول
    val longDiff = kaabaLngRad - userLngRad

    // معادلة حساب الاتجاه على الكرة الأرضية
    val y = sin(longDiff) * cos(kaabaLatRad)

    val x = cos(userLatRad) * sin(kaabaLatRad) -
            sin(userLatRad) * cos(kaabaLatRad) * cos(longDiff)

    // حساب الزاوية باستخدام atan2
    val bearing = Math.toDegrees(atan2(y, x))

    // ضبط الزاوية لتكون بين 0 و 360
    return bearing.normalizeDegrees()
}

/**
 * ضبط الزاوية ضمن المجال 0 → 360
 */
internal fun Double.normalizeDegrees(): Float =
    ((this + 360.0) % 360.0).toFloat()

/**
 * نسخة Float من دالة ضبط الزاوية
 */
internal fun Float.normalizeDegrees(): Float {
    var value = this % 360f
    if (value < 0f) value += 360f
    return value
}

/**
 * تحويل الزاوية إلى المجال -180 → +180
 * مفيد عند حساب فرق الاتجاه
 */
internal fun Float.normalizeSignedDegrees(): Float {
    val normalized = normalizeDegrees()
    return if (normalized > 180f) normalized - 360f else normalized
}

/**
 * معالجة دوران البوصلة لمنع القفز المفاجئ
 * عند الانتقال مثلًا من 359° إلى 0°
 */
internal fun Float.sanitizeRotation(previous: Float): Float {
    var delta = this - previous

    if (delta > 180f) {
        return this - 360f
    }

    if (delta < -180f) {
        return this + 360f
    }

    return this
}

/**
 * حساب الفرق الزاوي بين اتجاهين
 *
 * currentAzimuth : اتجاه الهاتف
 * targetAzimuth  : اتجاه القبلة
 *
 * النتيجة:
 * موجبة = القبلة على اليمين
 * سالبة = القبلة على اليسار
 */
fun bearingDifference(currentAzimuth: Float, targetAzimuth: Float): Float {
    val diff = (targetAzimuth - currentAzimuth + 540f) % 360f - 180f
    return diff
}

/**
 * تحويل الزاوية إلى اتجاه بوصلة نصي
 * مثل: N, NE, E, SE, S, SW, W, NW
 */
fun Float.toCardinalDirection(): String {

    if (this.isNaN()) return ""

    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")

    val normalized = normalizeDegrees()

    // تحديد المؤشر المناسب للاتجاه
    val index = ((normalized + 22.5f) / 45f).toInt() % directions.size

    return directions[index]
}