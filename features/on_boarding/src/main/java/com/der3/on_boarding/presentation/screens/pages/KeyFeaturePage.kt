package com.der3.on_boarding.presentation.screens.pages


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.components.HorizontalDotsIndicator
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale


@Composable
fun KeyFeaturePage(
    modifier: Modifier = Modifier,
    currentPage: Int = 1,
    totalPages: Int = 3,
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        // Top Row (Skip + Arrow)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                onPrevious()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = AppColors.gray900Text
                )
            }

            Text(
                text = stringResource(id = R.string.onboarding_skip),
                color = AppColors.green800,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onSkip() }
            )
        }

        Spacer(Modifier.height(40.dp))

        // Image Card (Green Voice Card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .shadow(10.dp, RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp))
                .background(
                    if (isDarkTheme) {
                        Brush.verticalGradient(
                            listOf(
                                AppColors.cardColor,
                                AppColors.green100.copy(alpha = 0.4f)
                            )
                        )
                    } else {
                        Brush.verticalGradient(listOf(AppColors.green100, AppColors.green100))
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else Color.Transparent,
                    shape = RoundedCornerShape(28.dp)
                ),
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
            text = stringResource(id = R.string.onboarding_title_2),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = AppColors.gray900Text,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(12.dp))

        // Description
        Text(
            text = stringResource(id = R.string.onboarding_desc_2),
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
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.green700)
        ) {

            Text(
                text = stringResource(id = R.string.onboarding_next),
                color = AppColors.white,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(10.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = AppColors.white
            )
        }

        Spacer(Modifier.height(10.dp))
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun KeyFeaturePageLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        KeyFeaturePage()
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun KeyFeaturePageDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        KeyFeaturePage()
    }
}