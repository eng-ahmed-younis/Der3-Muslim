package com.der3.home.presentations.favorite.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun EmptyFavoritesView(
    modifier: Modifier = Modifier,
    onNavigateToAzkar: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.green25),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = AppColors.green800.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = AppColors.green800
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.favorites_empty_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.W900,
                letterSpacing = 0.1.sp,
                fontFamily = FontFamily(Font(R.font.cairo_bold)),
                color = AppColors.gray900Text,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.favorites_empty_desc),
                fontSize = 14.sp,
                color = AppColors.gray500,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily(Font(R.font.cairo)),
                letterSpacing = 0.1.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = AppColors.green700,
                        shape = RoundedCornerShape(16.dp)
                    ).clickable {
                        onNavigateToAzkar()
                    }
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 30.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = AppColors.white
                    )

                    Text(
                        text = stringResource(id = R.string.browse_azkar),
                        color = AppColors.white,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily(Font(R.font.cairo)),
                        letterSpacing = 0.1.sp
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun EmptyFavoritesViewPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        EmptyFavoritesView(
            onNavigateToAzkar = {}
        )
    }
}
