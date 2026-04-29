package com.der3.sections.presentation.prayer.geocoder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun GeocoderControls(
    onBackClick: () -> Unit,
    onMyLocationClick: () -> Unit,
    onMapStyleClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.background(AppColors.cardColor.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = AppColors.green800
                )
            }
        }

        // Right-side FAB stack (MyLocation + Layers)
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(top = 8.dp, end = 16.dp)
                .zIndex(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FloatingActionButton(
                onClick = onMyLocationClick,
                containerColor = AppColors.cardColor.copy(alpha = 0.8f),
                contentColor = AppColors.green800,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "My Location")
            }

            FloatingActionButton(
                onClick = onMapStyleClick,
                containerColor = AppColors.cardColor.copy(alpha = 0.8f),
                contentColor = AppColors.green800,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Layers, contentDescription = "Map Style")
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GeocoderControlsLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            GeocoderControls(
                onBackClick = {},
                onMyLocationClick = {},
                onMapStyleClick = {}
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GeocoderControlsDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            GeocoderControls(
                onBackClick = {},
                onMyLocationClick = {},
                onMapStyleClick = {}
            )
        }
    }
}
