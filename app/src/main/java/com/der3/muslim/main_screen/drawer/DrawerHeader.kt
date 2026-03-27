package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
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
            .background(Color(0xFF0F5F2F))
            .padding(vertical = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        // Concentric circles effect can be simulated with nested boxes or a custom modifier, 
        // but for now, let's focus on the layout and colors.

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .offset(y = ((-20).dp)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.nav_header),
                    contentDescription = "Nav Header",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .offset(y = (55.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.der3_muslim_title),
                color = AppColors.white,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.cairo_bold)),
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .padding(top = 2.dp)
            )

            Text(
                text = stringResource(id = R.string.der3_muslim_subtitle),
                color = AppColors.gold400,
                fontFamily = FontFamily(Font(R.font.cairo_medium)),
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