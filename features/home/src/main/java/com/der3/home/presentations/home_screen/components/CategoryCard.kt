package com.der3.home.presentations.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.models.CategoryUi
import com.der3.ui.themes.Der3MuslimTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.der3.model.AppStyle
import com.der3.shared.data.provider.ZekrCategoriesProvider
import com.der3.ui.R
import com.der3.ui.models.PalletColors
import com.der3.ui.themes.AppColors

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: CategoryUi,
    onCategoryClick: (CategoryUi) -> Unit
) {


    Card(
        modifier = modifier
            .clickable(onClick = { onCategoryClick(category) }),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardColor
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        border = if (com.der3.ui.themes.isDarkTheme) androidx.compose.foundation.BorderStroke(
            1.dp,
            AppColors.green700.copy(alpha = 0.2f)
        ) else null
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        //bgColor, // 👈 random background
                        color = category.iconBg,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    category.icon,
                    contentDescription = null,
                   // tint = Color.Black // better contrast for mixed palette
                    tint = category.iconTint
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = category.title,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = AppColors.gray900Text
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = category.count,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.cairo_medium)),
                color = AppColors.gray500
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun CategoryCardLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        CategoryCard(
            category = ZekrCategoriesProvider.categories.first(),
            onCategoryClick = {}
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun CategoryCardDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        CategoryCard(
            category = ZekrCategoriesProvider.categories.first(),
            onCategoryClick = {}
        )
    }
}
