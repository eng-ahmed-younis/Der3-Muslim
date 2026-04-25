package com.der3.home.presentations.masbaha_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityItem(activity: MasbahaHistoryEntity, modifier: Modifier = Modifier) {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale("ar"))
    val time = timeFormat.format(Date(activity.timestamp))

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.zekrPanelBg)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.zekrScreenBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Stars,
                    contentDescription = null,
                    tint = AppColors.zekrPanelProgress,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.zekrText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.gray900Text
                )
                Text(
                    text = stringResource(R.string.history_item_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.zekrSubText
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = String.format(Locale("ar"), "%,d", activity.count),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.zekrPanelProgress
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.zekrSubText
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ActivityItemPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            ActivityItem(
                activity = MasbahaHistoryEntity(
                    zekrText = "سبحان الله",
                    count = 33,
                    zekrId = 1,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ActivityItemDarkPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            ActivityItem(
                activity = MasbahaHistoryEntity(
                    zekrText = "سبحان الله",
                    count = 33,
                    zekrId = 1,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}
