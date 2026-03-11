package com.der3.home.presentations.masbaha_history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.der3.ui.R
import com.der3.ui.themes.AppColors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun RecentActivityHeader(
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.history_recent_activity),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.green800
        )
        Text(
            text = stringResource(R.string.history_view_all),
            style = MaterialTheme.typography.bodySmall,
            color = AppColors.green800,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onViewAll() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecentActivityHeaderPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            RecentActivityHeader(onViewAll = {})
        }
    }
}
