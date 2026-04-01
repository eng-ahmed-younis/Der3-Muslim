package com.der3.home.presentations.notification.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun DeleteAllNotificationsDialog(
    count: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.cardColor,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = stringResource(id = R.string.delete_all_notifications_title),
                color = AppColors.green900,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_all_notifications_message, count),
                color = AppColors.gray500,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.red900)
            ) {
                Text(
                    text = stringResource(id = R.string.delete_confirm),
                    fontWeight = FontWeight.Bold,
                    color = AppColors.white
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = AppColors.gray400,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    )
}

@Preview(showBackground = true, locale = "ar")
@Preview(showBackground = true, locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeleteAllNotificationsDialogPreview() {
    Der3MuslimTheme(
        style = if (isSystemInDarkTheme()) AppStyle.DARK else AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        DeleteAllNotificationsDialog(count = 5, onDismiss = {}, onConfirm = {})
    }
}
