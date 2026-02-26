package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F6B2D))
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.mosque),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                // colorFilter = ColorFilter.tint(Color(0xFF2E7D32)),
                modifier = Modifier.size(100.dp)
            )


         //   Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "تطبيق أذكار",
                color = AppColors.white,
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(com.der3.muslim.R.font.cairo_bold)),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "حصنك اليومي",
                modifier = Modifier
                    .offset(y = (-4).dp),
                color = AppColors.gold400,
                fontFamily = FontFamily(Font(com.der3.muslim.R.font.cairo_extralight)),
                fontSize = 14.sp
            )
        }
    }
}


@Preview(showBackground = true, name = "Drawer Header Preview")
@Composable
private fun DrawerHeaderPreview() {
    Der3MuslimTheme {
        DrawerHeader()
    }
}