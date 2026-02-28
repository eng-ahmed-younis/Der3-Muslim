package com.der3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.DaysSelector
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
private fun TimePickerCard(
    hour: Int,
    minute: Int,
    isAm: Boolean,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onAmPmChange: (Boolean) -> Unit
) {

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.white)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AmPmSelector(isAm, onAmPmChange)

            TimeNumber(hour)
            Text(":", fontSize = 26.sp)
            TimeNumber(minute)
        }
    }
}


@Composable
private fun AmPmSelector(
    isAm: Boolean,
    onChange: (Boolean) -> Unit
) {
    Column {
        AmPmChip("ص", isAm) { onChange(true) }
        Spacer(Modifier.height(8.dp))
        AmPmChip("م", !isAm) { onChange(false) }
    }
}

@Composable
private fun AmPmChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (selected) AppColors.green700 else AppColors.gray100
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else AppColors.gray500
        )
    }
}


@Composable
fun DaysSelectorView(
    modifier: Modifier = Modifier,
    selectedDays: Set<String>,
    onToggle: (String) -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        DaysSelector.entries.forEach { day ->
            val selected = selectedDays.contains(day.shortName)

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected) AppColors.green700 else AppColors.gray100
                    )
                    .clickable { onToggle(day.shortName) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.shortName,
                    color = if (selected) Color.White else AppColors.green800,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}




@Composable
private fun SaveButton(onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(64.dp),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.green800
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.save),
            contentDescription = null,
            tint = AppColors.white
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "حفظ التنبيه",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun TimeNumber(
    value: Int,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(width = 90.dp, height = 80.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.green50)
            .border(
                width = 1.dp,
                color = AppColors.green100,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AppColors.green800
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F6F3) // Use AppColors.gray50
@Composable
private fun TimePickerScreenPreview() {
    var hour by remember { mutableStateOf(5) }
    var minute by remember { mutableStateOf(30) }
    var isAm by remember { mutableStateOf(true) }
    var selectedDays by remember { mutableStateOf(setOf("س", "ج")) }
    var isEnabled by remember { mutableStateOf(true) }

    Der3MuslimTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ضبط التنبيه",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.green900
            )

            TimePickerCard(
                hour = hour,
                minute = minute,
                isAm = isAm,
                onHourChange = { hour = it },
                onMinuteChange = { minute = it },
                onAmPmChange = { isAm = it }
            )

            DaysSelectorView(
                selectedDays = selectedDays,
                onToggle = { day ->
                    selectedDays = if (selectedDays.contains(day)) {
                        selectedDays - day
                    } else {
                        selectedDays + day
                    }
                }
            )



            Spacer(modifier = Modifier.weight(1f))

            SaveButton(onClick = { })
        }
    }
}