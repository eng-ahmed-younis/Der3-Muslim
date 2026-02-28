import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.der3.model.DayPeriod
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun AmPmToggle(
    modifier: Modifier = Modifier,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        DayPeriod.entries.forEach { label ->
            val isActive = selected == label.value
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = 52.dp, height = 40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isActive) AppColors.green800 else AppColors.green50)
                    .then(
                        Modifier.pointerInput(Unit) {
                            detectVerticalDragGestures { _, _ -> }
                        }
                    )
            ) {
                TextButton(
                    onClick = { onSelect(label.value) },
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = label.value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) AppColors.white else AppColors.green500
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "AM/PM Toggle Preview")
@Composable
private fun AmPmTogglePreview() {
    var selectedPeriod by remember { mutableStateOf("ص") }

    Der3MuslimTheme {
        Box(
            modifier = Modifier
                .background(AppColors.gray50)
                .padding(24.dp)
        ) {
            AmPmToggle(
                selected = selectedPeriod,
                onSelect = { selectedPeriod = it }
            )
        }
    }
}
