package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(
                color =  AppColors.screenBackground
            ).clip(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 12.dp,
                    topEnd = 0.dp,
                    bottomEnd = 12.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Concentric circles effect can be simulated with nested boxes or a custom modifier,
        // but for now, let's focus on the layout and colors.


                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Nav Header",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )


/*
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .offset(y = (55.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.der3_muslim_title),
                color = Color.White,
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
        }*/

    }
}


@Preview(showBackground = true, name = "Drawer Header Preview")
@Composable
private fun DrawerHeaderPreview() {
    Der3MuslimTheme {
        DrawerHeader()
    }
}