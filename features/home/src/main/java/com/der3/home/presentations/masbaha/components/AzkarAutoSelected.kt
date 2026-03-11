package com.der3.home.presentations.masbaha.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun AzkarAutoSelected(
    autoSwitch: Boolean,
    onAutoSwitchChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.select_zekr),
            color = AppColors.green800,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
         //   horizontalArrangement = Alignment.CenterEnd
        ) {
            Text(
                text = stringResource(id = R.string.auto_change),
                color = AppColors.gold500,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                modifier = Modifier.scale(0.7f),
                checked = autoSwitch,
                onCheckedChange = onAutoSwitchChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppColors.white,
                    checkedTrackColor = AppColors.green800,
                    uncheckedThumbColor = AppColors.gray500,
                    uncheckedTrackColor = AppColors.gray200,
                    uncheckedBorderColor = AppColors.gray500
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AzkarAutoSelectedPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AzkarAutoSelected(
            autoSwitch = true,
            onAutoSwitchChange = {}
        )
    }
}
