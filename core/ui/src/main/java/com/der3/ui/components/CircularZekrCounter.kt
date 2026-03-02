import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale



@Composable
fun CircularZekrCounter(
    modifier: Modifier = Modifier,
    progress: Float,
    count: Int,
    total: Int,
    backgroundColor: Color = AppColors.gray100,
    progressColor: Color = AppColors.gold600,
    onClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp



    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(screenWidth * 0.60f)
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = 12.dp.toPx()

            // ← light gray background circle
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // ← gold progress arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }

        // count + total text — shifted up to make room for FAB
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .offset(y = (-10).dp)
        ) {
            Text(
                text = "$count",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.cairo_bold)),
                fontSize = 35.sp,
                color = AppColors.gray900Text
            )

            Text(
                text = "من $total",
                color = AppColors.green700,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.cairo_medium)),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // ← FAB pinned to bottom center of the Box
        FloatingActionButton(
            onClick = onClick,
            containerColor = AppColors.green800,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 20.dp)  // ← overlaps the circle edge
        ) {
            Icon(
                imageVector = Icons.Default.TouchApp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun CircularZekrCounterPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularZekrCounter(
                progress = 0.36f,
                count = 11,
                total = 33,
                onClick = {},
                backgroundColor = AppColors.gray100,
                progressColor = AppColors.gold600
            )
        }
    }
}