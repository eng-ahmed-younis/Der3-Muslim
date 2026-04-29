package com.der3.sections.presentation.prayer.geocoder.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun GeocoderMarker(
    modifier: Modifier = Modifier
) {
    // White on dark map (gold roads) → strong contrast
    // Green on light map → clear and on-brand
    val ringColor  = if (isDarkTheme) Color.White          else AppColors.green800
    val dotColor   = if (isDarkTheme) AppColors.gold500    else Color.White

    // 80×80 dp canvas — its center (40, 40) lands exactly on the screen
    // center point (the selected location) because the parent places this
    // composable with Alignment.Center.
    Canvas(modifier = modifier.size(80.dp)) {
        val cx = size.width  / 2f
        val cy = size.height / 2f

        val ringRadius  = 22.dp.toPx()
        val strokeW     = 2.dp.toPx()

        // ── Ambient glow (large, very faint) ──────────────────────────────
        drawCircle(
            color  = ringColor.copy(alpha = 0.07f),
            radius = 36.dp.toPx(),
            center = Offset(cx, cy)
        )

        // ── Inner translucent fill ─────────────────────────────────────────
        drawCircle(
            color  = ringColor.copy(alpha = 0.12f),
            radius = 18.dp.toPx(),
            center = Offset(cx, cy)
        )

        // ── Main ring ─────────────────────────────────────────────────────
        drawCircle(
            color  = ringColor.copy(alpha = 0.85f),
            radius = ringRadius,
            center = Offset(cx, cy),
            style  = Stroke(width = strokeW)
        )

        // ── Crosshair arms (N / S / W / E) ───────────────────────────────
        val armStart = 25.dp.toPx()
        val armEnd   = 34.dp.toPx()
        val armColor = ringColor.copy(alpha = 0.85f)

        drawLine(armColor, Offset(cx, cy - armStart), Offset(cx, cy - armEnd), strokeW)
        drawLine(armColor, Offset(cx, cy + armStart), Offset(cx, cy + armEnd), strokeW)
        drawLine(armColor, Offset(cx - armStart, cy), Offset(cx - armEnd, cy), strokeW)
        drawLine(armColor, Offset(cx + armStart, cy), Offset(cx + armEnd, cy), strokeW)

        // ── Center anchor dot ─────────────────────────────────────────────
        drawCircle(
            color  = dotColor,
            radius = 5.dp.toPx(),
            center = Offset(cx, cy)
        )

        // ── Tiny white highlight on the dot ───────────────────────────────
        drawCircle(
            color  = Color.White.copy(alpha = 0.85f),
            radius = 2.dp.toPx(),
            center = Offset(cx, cy)
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GeocoderMarkerLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(AppColors.screenBackground),
            contentAlignment = Alignment.Center
        ) {
            GeocoderMarker()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GeocoderMarkerDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(AppColors.screenBackground),
            contentAlignment = Alignment.Center
        ) {
            GeocoderMarker()
        }
    }
}
