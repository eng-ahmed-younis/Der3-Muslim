package com.der3.on_boarding.presentation.screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun OnBoardingIndicator(
    currentPage: Int,
    totalPages: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(totalPages) { index ->
            val selected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (selected) 38.dp else 12.dp,
                label = ""
            )

            val color by animateColorAsState(
                targetValue = if (selected)
                    AppColors.green700
                else
                    AppColors.gray100,
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .height(10.dp)
                    .width(width)
                    .clip(RoundedCornerShape(100))
                    .background(color)
            )
        }
    }
}


@Preview(showBackground = true, name = "OnBoarding Indicators States")
@Composable
private fun OnBoardingIndicatorPreview() {
    Der3MuslimTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // State: First page selected
            OnBoardingIndicator(currentPage = 0, totalPages = 3)

            Spacer(modifier = Modifier.height(16.dp))

            // State: Second page selected
            OnBoardingIndicator(currentPage = 1, totalPages = 3)

            Spacer(modifier = Modifier.height(16.dp))

            // State: Last page selected
            OnBoardingIndicator(currentPage = 2, totalPages = 3)
        }
    }
}