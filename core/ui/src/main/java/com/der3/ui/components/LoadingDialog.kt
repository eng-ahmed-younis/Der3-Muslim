package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale


@Composable
fun LoadingDialog(
    visible: Boolean,
    title: String = stringResource(id = R.string.loading_title),
    message: String = stringResource(id = R.string.loading_message),
) {
    if (!visible) return

    Dialog(
        onDismissRequest = {}, // Prevent dismiss
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.cardColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_shine),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = if (isDarkTheme) AppColors.gold700 else AppColors.green800
                    )
                    CircularProgressIndicator(
                        strokeWidth = 5.dp,
                        color = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                        trackColor = AppColors.green50,
                        modifier = Modifier.size(50.dp)
                    )
                }



                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppColors.gray900Text,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = AppColors.gray500,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun LoadingDialogLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = Color.Black.copy(alpha = 0.5f)) {
            LoadingDialog(
                visible = true,
                title = "جاري التحميل",
                message = "يرجى الانتظار قليلاً، نحن نقوم بتجهيز المحتوى لك"
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoadingDialogDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = Color.Black.copy(alpha = 0.5f)) {
            LoadingDialog(
                visible = true,
                title = "جاري التحميل",
                message = "يرجى الانتظار قليلاً، نحن نقوم بتجهيز المحتوى لك"
            )
        }
    }
}
