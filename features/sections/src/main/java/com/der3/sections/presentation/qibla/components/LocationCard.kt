package com.der3.sections.presentation.qibla.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.sections.presentation.qibla.mvi.QiblaState
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun LocationCard(state: QiblaState, modifier: Modifier = Modifier) {
    val isDark = com.der3.ui.themes.isDarkTheme
    val accentColor = if (isDark) AppColors.gold700 else AppColors.green700

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = if (isDark) BorderStroke(
            1.dp,
            accentColor.copy(alpha = 0.2f)
        ) else null
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(id = com.der3.ui.R.string.current_location_label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.currentLocationName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = AppColors.gray900Text
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = com.der3.ui.R.string.distance_to_kaaba,
                    String.format(
                        Locale.forLanguageTag("ar"),
                        "%.1f",
                        state.distanceToKaaba
                    )
                ),
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.gray500
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun LocationCardPreviewLight() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        LocationCard(
            state = QiblaState(
                currentLocationName = "القاهرة، مصر",
                distanceToKaaba = 1250.5
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LocationCardPreviewDark() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        LocationCard(
            state = QiblaState(
                currentLocationName = "القاهرة، مصر",
                distanceToKaaba = 1250.5
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
