package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    retryText: String? = null,
    dismissText: String? = null,
    visible: Boolean = true,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible) return
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(24.dp),
            color = AppColors.cardColor,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .widthIn(min = 280.dp, max = 420.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Icon Section
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = if (isDarkTheme) AppColors.gold700.copy(alpha = 0.1f) else AppColors.green50,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_shine),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = if (isDarkTheme) AppColors.gold700 else AppColors.green800
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title ?: "حدث خطأ غير متوقع",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = if (isDarkTheme) AppColors.gray900Text else AppColors.green900
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = message ?: "حدث خطأ غير متوقع، يرجى المحاولة مرة أخرى",
                    fontSize = 14.sp,
                    color = AppColors.gray500,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) AppColors.gold700 else AppColors.green800
                    )
                ) {
                    Text(
                        text = retryText ?: "إعادة المحاولة",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dismissText ?: "إغلاق",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W800,
                        color = AppColors.gray400
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun ErrorDialogLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = Color.Black.copy(alpha = 0.5f)) {
            ErrorDialog(
                onRetry = {},
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ErrorDialogDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = Color.Black.copy(alpha = 0.5f)) {
            ErrorDialog(
                onRetry = {},
                onDismiss = {}
            )
        }
    }
}
