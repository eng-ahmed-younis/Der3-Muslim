package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSizeBottomSheet(
    isVisible: Boolean,
    currentFontSize: Float,
    onDismiss: () -> Unit,
    onSave: (Float) -> Unit,
    onReset: () -> Unit
) {


    var sliderPosition by remember {
        mutableFloatStateOf(currentFontSize)
    }

// Sync when parent changes the value
    LaunchedEffect(currentFontSize) {
        sliderPosition = currentFontSize
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        if (isVisible) {
            ModalBottomSheet(
                onDismissRequest = onDismiss,
                dragHandle = null,
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                ),
                containerColor = AppColors.zekrPanelBg
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 16.dp
                        )
                ) {

                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = AppColors.gray900Text
                            )
                        }

                        Text(
                            text = "حجم الخط",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily(Font(R.font.cairo_bold)),
                            color = AppColors.green800
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Slider Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(AppColors.zekrScreenBg)
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "أ",
                                fontSize = 14.sp,
                                color = AppColors.gray900Text
                            )

                            Slider(
                                value = sliderPosition,
                                onValueChange = { sliderPosition = it },
                                // Discrete Steps: By adding steps, the slider won't stop at awkward values like 18.342f. It will snap to increments of 1f (e.g., 14, 15, 16...).
                                steps = (SliderFontSizeEnum.MAX_FONT.value - SliderFontSizeEnum.MIN_FONT.value).toInt() - 1,
                                valueRange = SliderFontSizeEnum.MIN_FONT.value..SliderFontSizeEnum.MAX_FONT.value,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = AppColors.zekrPanelProgress,
                                    activeTrackColor = AppColors.zekrPanelProgress,
                                    inactiveTrackColor = AppColors.zekrPanelTrack
                                )
                            )

                            Text(
                                text = "أ",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.gray900Text
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Save Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSave(sliderPosition) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircleOutline,
                            contentDescription = null,
                            tint = AppColors.green800
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "حفظ الإعدادات",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.green800
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reset Option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onReset() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.TextFields,
                            contentDescription = null,
                            tint = AppColors.gray500
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "الخط الافتراضي",
                            fontSize = 16.sp,
                            color = AppColors.gray500
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}


enum class SliderFontSizeEnum(val value: Float) {
    MIN_FONT(value = 14f),
    MAX_FONT(value = 30f)
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun FontSizeBottomSheetPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        FontSizeBottomSheet(
            isVisible = true,
            currentFontSize = 20f,
            onDismiss = {},
            onSave = {},
            onReset = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FontSizeBottomSheetDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        FontSizeBottomSheet(
            isVisible = true,
            currentFontSize = 20f,
            onDismiss = {},
            onSave = {},
            onReset = {}
        )
    }
}
