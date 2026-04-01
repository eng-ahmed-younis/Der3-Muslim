package com.der3.muslim.main_screen.drawer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun DrawerFooter(
    modifier: Modifier = Modifier,
    version: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.cardColor)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.version_title),
                    color = AppColors.gray500,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.cairo)),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = version,
                    color = AppColors.gold600,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.cairo)),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun DrawerFooterLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            DrawerFooter(version = "1.0.0 (24)")
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DrawerFooterDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            DrawerFooter(version = "1.0.0 (24)")
        }
    }
}
