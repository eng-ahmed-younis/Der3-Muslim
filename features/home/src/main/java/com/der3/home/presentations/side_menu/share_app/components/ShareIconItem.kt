package com.der3.home.presentations.side_menu.share_app.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ShareIconItem(
    @StringRes label: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    val colors = AppColors
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.gray100.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = colors.green800,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = label),
            fontSize = 16.sp,
            color = colors.gray900Text,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, name = "Arabic", locale = "ar")
@Composable
fun ShareIconItemPreviewAr() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            ShareIconItem(
                label = R.string.twitter,
                icon = R.drawable.twitter,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "English")
@Composable
fun ShareIconItemPreviewEn() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("en").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            ShareIconItem(
                label = R.string.whatsapp,
                icon = R.drawable.whatsapp,
                onClick = {}
            )
        }
    }
}
