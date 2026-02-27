package com.der3.on_boarding.presentation.screens.pages


import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.*
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
fun KeyFeaturePage(
    modifier: Modifier = Modifier,
    currentPage: Int = 1,
    totalPages: Int = 3,
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.gray50)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {

            // Top Row (Skip + Arrow)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "تخطي",
                    color = AppColors.green800,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onSkip() }
                )

                IconButton(onClick = {
                    onPrevious()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            // Image Card (Green Voice Card)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                   // .shadow(8.dp, RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(AppColors.green100),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.on_boarding_img_2),
                    contentDescription = "voice feature",
                    modifier = Modifier.size(160.dp),
                    contentScale = ContentScale.Fit
                )

            }

            Spacer(Modifier.height(32.dp))

            // Title
            Text(
                text = "اسمع بصوت واضح",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColors.gray900Text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(12.dp))

            // Description
            Text(
                text = "تشغيل صوت لكل ذكر لمساعدتك على الحفظ\nوالتدبر بخشوع وطمأنينة",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColors.gray500,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(22.dp))

            // Indicator
            HorizontalDotsIndicator(
                currentPage = currentPage,
                totalPages = totalPages
            )

            Spacer(Modifier.weight(1f))

            // Next Button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.green700)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
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



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KeyFeaturePagePreview(){
    Der3MuslimTheme {
        KeyFeaturePage()
    }
}