package com.der3.sections.presentation.qibla.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

/**
 * CompassDial draws the circular dial of the compass including cardinal directions.
 */
@Composable
fun CompassDial(modifier: Modifier = Modifier) {
    val isDark = com.der3.ui.themes.isDarkTheme
    val gray200 = AppColors.gray200
    val textColor = if (isDark) AppColors.gray900Text.copy(alpha = 0.6f) else AppColors.gray500

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Outer faint circle
            drawCircle(
                color = gray200.copy(alpha = if (isDark) 0.3f else 0.5f),
                style = Stroke(width = 1.dp.toPx())
            )
            // Dotted circle
            drawCircle(
                color = gray200,
                radius = size.width / 2 * 0.85f,
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )
        }
        Text(
            text = stringResource(id = com.der3.ui.R.string.north_short),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = stringResource(id = com.der3.ui.R.string.south_short),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = stringResource(id = com.der3.ui.R.string.east_short),
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = stringResource(id = com.der3.ui.R.string.west_short),
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 10.dp),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun CompassDialPreviewLight() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
            CompassDial()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CompassDialPreviewDark() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
            CompassDial()
        }
    }
}
