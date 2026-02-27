package com.der3.on_boarding.presentation.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.der3.ui.components.HorizontalDotsIndicator
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun GetStartedPage(
    modifier: Modifier = Modifier,
    currentPage: Int = 2,
    totalPages: Int = 3,
    onBack: () -> Unit = {},
    onFinish: () -> Unit = {}
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.gray50)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {

            // Top Bar (Title + Arrow)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "الأذكار",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.gray900Text
                )

                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            // Image Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.on_boarding_img_3),
                    contentDescription = "counter feature",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(28.dp))

            // Title
            Text(
                text = "تتبع عدد التكرار",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.gray900Text
            )

            Spacer(Modifier.height(12.dp))

            // Description
            Text(
                text = "عداد ذكي لإكمال ذكرك اليومي بكل\nسهولة ويسر",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.gray500
            )

            Spacer(Modifier.height(22.dp))

            // Indicator
            HorizontalDotsIndicator(
                currentPage = currentPage,
                totalPages = totalPages
            )

            Spacer(Modifier.weight(1f))

            // Start Button
            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.green700
                )
            ) {
                Text(
                    text = "ابدأ الآن",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}


@Preview(showBackground = true, name = "Get Started Page Preview")
@Composable
private fun GetStartedPagePreview() {
    Der3MuslimTheme {
        GetStartedPage(
            onBack = {},
            onFinish = {}
        )
    }
}
