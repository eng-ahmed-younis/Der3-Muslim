package com.der3.on_boarding.presentation.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.der3.on_boarding.presentation.screens.components.OnBoardingIndicator
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun WelcomePage(
    modifier: Modifier = Modifier,
    currentPage: Int = 0,
    totalPages: Int = 3,
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    // Force RTL for Arabic UI
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.gray50)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            // Top: Skip pill (تخطي)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(AppColors.green100)
                        .clickable { onSkip() }
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "تخطي",
                        color = AppColors.green800,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(36.dp))

            // Image Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .shadow(10.dp, RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
            ) {
                // Replace with your image resource
                Image(
                    painter = painterResource(id = R.drawable.on_boarding_img_1),
                    contentDescription = "first on board screen",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(26.dp))

            // Title
            Text(
                text = "اقرأ الأذكار بسهولة",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColors.gray900Text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(12.dp))

            // Description
            Text(
                text = "جميع الأذكار مرتبة حسب الفئات لتسهيل" +
                        "\nالوصول إليها في كل وقت وحين",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColors.gray500,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(22.dp))

            // Dots (indicator)
            OnBoardingIndicator(
                currentPage = currentPage,
                totalPages = totalPages
            )

            Spacer(Modifier.weight(1f))

            // Bottom big button: التالي + arrow
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.green700)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "التالي",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WelcomePagePreview(){
    Der3MuslimTheme {
        WelcomePage()
    }
}