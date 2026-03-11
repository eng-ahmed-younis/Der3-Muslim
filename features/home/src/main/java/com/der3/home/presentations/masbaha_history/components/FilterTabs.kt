package com.der3.home.presentations.masbaha_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.themes.AppColors

import androidx.compose.ui.tooling.preview.Preview
import com.der3.model.HistoryFilter
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun FilterTabs(
    modifier: Modifier = Modifier,
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(color = AppColors.green100)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HistoryFilter.entries.reversed().forEach { filter ->
            val isSelected = selectedFilter == filter
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) AppColors.gray50 else Color.Transparent)
                    .clickable { onFilterSelected(filter) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (filter) {
                        HistoryFilter.DAY -> stringResource(R.string.history_filter_today)
                        HistoryFilter.WEEK -> stringResource(R.string.history_filter_week)
                        HistoryFilter.MONTH -> stringResource(R.string.history_filter_month)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) AppColors.green800 else AppColors.gray500,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterTabsPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            FilterTabs(
                selectedFilter = HistoryFilter.DAY,
                onFilterSelected = {}
            )
        }
    }
}
