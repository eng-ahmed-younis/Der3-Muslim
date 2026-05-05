package com.der3.sections.presentation.qibla.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

/**
 * CompassNeedle draws the needle pointing towards the Qibla.
 * It includes a shaft with green and gold parts and an arrow head.
 */
@Composable
fun CompassNeedle(modifier: Modifier = Modifier) {
    val isDark = com.der3.ui.themes.isDarkTheme
    // Using green400 for dark mode as it's a more solid green in the dark palette
    val greenPart = if (isDark) AppColors.green400 else AppColors.green800
    val gold = AppColors.gold700
    
    Canvas(modifier = modifier.size(240.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        
        // Needle Shaft
        // Green Tail
        drawLine(
            color = greenPart,
            start = center,
            end = Offset(centerX, centerY + 80.dp.toPx()),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
        
        // Gold Shaft
        drawLine(
            color = gold,
            start = center,
            end = Offset(centerX, centerY - 80.dp.toPx()),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
        
        // Arrow Head
        val headPath = Path().apply {
            moveTo(centerX, centerY - 105.dp.toPx()) // Tip
            lineTo(centerX - 12.dp.toPx(), centerY - 80.dp.toPx())
            lineTo(centerX + 12.dp.toPx(), centerY - 80.dp.toPx())
            close()
        }
        drawPath(headPath, color = gold)
        
        // Center Hub
        drawCircle(
            color = greenPart,
            radius = 10.dp.toPx(),
            center = center
        )
        drawCircle(
            color = gold,
            radius = 10.dp.toPx(),
            center = center,
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = center
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun CompassNeedlePreviewLight() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            CompassNeedle()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CompassNeedlePreviewDark() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            CompassNeedle()
        }
    }
}
