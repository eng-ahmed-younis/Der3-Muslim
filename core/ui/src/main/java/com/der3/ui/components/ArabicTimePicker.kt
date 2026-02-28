package com.example.timepicker

import AmPmToggle
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.components.arabic_timer.WheelColumn
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

// ─── Colors ───────────────────────────────────────────────────────────────────
val LightGreen        = Color(0xFFF0F7F0)
val SideValueColor    = Color(0xFFB8CFB8)



@SuppressLint("AutoboxingStateCreation")
@Composable
fun ArabicTimePicker(
    modifier: Modifier = Modifier,
    initialHour: Int = 30,
    initialMinute: Int = 5,
    onTimeChanged: (hour: Int, minute: Int, ampm: String) -> Unit = { _, _, _ -> }
) {
    var hour   by remember { mutableIntStateOf(initialHour) }
    var minute by remember { mutableIntStateOf(initialMinute) }
    var ampm   by remember { mutableStateOf("ص") }

    LaunchedEffect(hour, minute, ampm) {
        onTimeChanged(hour, minute, ampm)
    }

    Box(
        modifier = modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = .5.dp,
                color = AppColors.green25,
                shape = RoundedCornerShape(24.dp)
            )
            .background(AppColors.white),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(
                    color = AppColors.white
                )
                .padding(horizontal = 36.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hours
            WheelColumn(
                value = hour,
                min = 1,
                max = 12,
                onValueChange = { hour = it }
            )

            // Colon separator
            Text(
                text = ":",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.green900,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Hours
            WheelColumn(
                value = hour,
                min = 1,
                max = 12,
                onValueChange = { hour = it }
            )

            // AM/PM (rightmost in RTL)
            AmPmToggle(selected = ampm, onSelect = { ampm = it })

        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArabicTimePickerPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ArabicTimePicker(
            modifier = Modifier
                .background(
                    color = AppColors.gray50
                )
        )
    }

}