package com.der3.home.presentations.side_menu.setting.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.model.PlaybackSpeed
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun PlaybackSpeedDialog(
    currentSpeed: Float,
    onSpeedSelected: (Float) -> Unit,
    onDismiss: () -> Unit
) {


    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.settings_playback_speed),
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.gray900Text
            )
        },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                PlaybackSpeed.entries.forEach { speed ->
                    val isSelected = (speed.value == currentSpeed)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isSelected,
                                onClick = { onSpeedSelected(speed.value) },
                                role = androidx.compose.ui.semantics.Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AppColors.gold700,
                                unselectedColor = AppColors.gray400
                            )
                        )

                        Text(
                            text = speed.label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected) AppColors.gold700 else AppColors.gray900Text,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = stringResource(id = R.string.cancel),
                    color = AppColors.gold700
                )
            }
        },
        containerColor = AppColors.cardColor,
        shape = RoundedCornerShape(28.dp)
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PlaybackSpeedDialogPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        PlaybackSpeedDialog(
            currentSpeed = 1.0f,
            onSpeedSelected = {},
            onDismiss = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PlaybackSpeedDialogDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        PlaybackSpeedDialog(
            currentSpeed = 1.0f,
            onSpeedSelected = {},
            onDismiss = {}
        )
    }
}
