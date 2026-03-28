package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun TextSlider(
    longText: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    pageSize: Int = 150
) {
    // Split text into pages without breaking words
    val pages = remember(longText, pageSize) {
        val result = mutableListOf<String>()
        val words = longText.split("\\s+".toRegex())
        var currentPage = StringBuilder()

        for (word in words) {
            if (currentPage.length + word.length + 1 <= pageSize) {
                if (currentPage.isNotEmpty()) currentPage.append(" ")
                currentPage.append(word)
            } else {
                if (currentPage.isNotEmpty()) {
                    result.add(currentPage.toString())
                    currentPage = StringBuilder(word)
                } else {
                    // Word is longer than pageSize, just add it
                    result.add(word)
                }
            }
        }
        if (currentPage.isNotEmpty()) result.add(currentPage.toString())
        result.ifEmpty { listOf("") }
    }

    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )

    Column(
        modifier = modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 12.dp
        ) { page ->
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.cardColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(max = 350.dp)
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pages[page],
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = fontSize * 1.4,
                            textAlign = TextAlign.Center,
                            fontSize = fontSize
                        ),
                        color = AppColors.gray900Text
                    )
                }
            }
        }

        if (pages.size > 1) {
            Spacer(modifier = Modifier.height(24.dp))

            // Improved Indicator with Page Count
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${pagerState.currentPage + 1} / ${pages.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = AppColors.gray900Text,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        val width by animateDpAsState(
                            targetValue = if (isSelected) 20.dp else 8.dp,
                            animationSpec = tween(durationMillis = 300),
                            label = "dotWidth"
                        )

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(width = width, height = 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected)
                                        AppColors.green800
                                    else
                                        AppColors.gray200
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TextSliderLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            TextSlider(
                longText = "هذا نص تجريبي لعرض المكون. " +
                        "يمكن استخدامه لعرض الأذكار أو النصوص الطويلة " +
                        "التي يتم تقسيمها إلى صفحات متعددة لتسهيل القراءة."+
                        "التي يتم تقسيمها إلى صفحات متعددة لتسهيل القراءة."
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TextSliderDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            TextSlider(
                longText = "هذا نص تجريبي لعرض المكون. " +
                        "يمكن استخدامه لعرض الأذكار أو النصوص الطويلة " +
                        "التي يتم تقسيمها إلى صفحات متعددة لتسهيل القراءة."+
                        "التي يتم تقسيمها إلى صفحات متعددة لتسهيل القراءة."
            )
        }
    }
}
