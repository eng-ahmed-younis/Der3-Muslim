package com.der3.home.presentations.masbaha.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ResetMasbahaDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.white,
        title = {
            Text(
                text = stringResource(id = R.string.reset_confirmation_title),
                color = AppColors.green800,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.reset_confirmation_message),
                color = AppColors.gray500
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(id = R.string.reset_confirm),
                    color = AppColors.green800,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = AppColors.gray500
                )
            }
        }
    )
}

@Preview
@Composable
fun ResetMasbahaDialogPreview() {
    Der3MuslimTheme(language = Locale("ar")) {
        ResetMasbahaDialog(onDismiss = {}, onConfirm = {})
    }
}
