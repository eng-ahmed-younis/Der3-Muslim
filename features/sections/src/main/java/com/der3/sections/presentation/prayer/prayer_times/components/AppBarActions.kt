package com.der3.sections.presentation.prayer.prayer_times.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeIntent
import com.der3.ui.R
import com.der3.ui.themes.AppColors


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun AppBarActions(
    modifier: Modifier = Modifier,
    onIntent: (PrayerTimeIntent) -> Unit,
    onShowCalendar: () -> Unit,
    onShowLocation: () -> Unit,
    onShowMethod: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            onClick = {
                onIntent(PrayerTimeIntent.LoadMonthlyCalendar)
                onShowCalendar()
            },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar",
                tint = AppColors.gray500,
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(
            onClick = onShowLocation,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = AppColors.gray500,
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(
            onClick = onShowMethod,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = AppColors.gray500,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AppColors.gold500.copy(alpha = 0.12f))
                .clickable { onIntent(PrayerTimeIntent.OpenQibla) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_qibla),
                contentDescription = "Qibla",
                tint = AppColors.gold700,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBarActionsPreview() {
    Der3MuslimTheme {
        AppBarActions(
            onIntent = {},
            onShowCalendar = {},
            onShowLocation = {},
            onShowMethod = {}
        )
    }
}
