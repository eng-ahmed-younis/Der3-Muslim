package com.der3.sections.presentation.prayer.location_picker.map_style

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.GeographyMapStyle
import com.der3.ui.themes.AppColors
import com.der3.utils.AppConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapStyleBottomSheet(
    apiKey: String,
    selectedStyle: GeographyMapStyle,
    onDismiss: () -> Unit,
    onStyleSelected: (GeographyMapStyle) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.cardColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Choose Map Style",
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.gray900Text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            LazyColumn {
                item {
                    Text(
                        text = "Light Styles",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.gray900Text,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(GeographyMapStyle.getLightStyles()) { style ->
                    MapStyleItem(
                        style = style,
                        imageUrl = style.previewUrl(apiKey),
                        isSelected = style == selectedStyle,
                        onClick = {
                            onStyleSelected(style)
                            onDismiss()
                        }
                    )
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = AppColors.gray200
                    )
                    Text(
                        text = "Dark Styles",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.gray900Text,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(GeographyMapStyle.getDarkStyles()) { style ->
                    MapStyleItem(
                        style = style,
                        imageUrl = style.previewUrl(apiKey),
                        isSelected = style == selectedStyle,
                        onClick = {
                            onStyleSelected(style)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapStyleBottomSheetPreview() {
    MapStyleBottomSheet(
        apiKey = AppConfig.GEOAPIFY_API_KEY,
        selectedStyle = GeographyMapStyle.OsmBright,
        onDismiss = {},
        onStyleSelected = {}
    )
}
