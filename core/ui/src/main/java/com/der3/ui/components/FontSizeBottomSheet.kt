package com.der3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                containerColor = AppColors.white
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
                                contentDescription = "Close"
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
                            .background(Color(0xFFF3F5F4))
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
                                    thumbColor = AppColors.green700,
                                    activeTrackColor = AppColors.green500,
                                    inactiveTrackColor = AppColors.green100
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

@Preview(showBackground = true)
@Composable
fun FontSizeBottomSheetPreview() {
    Der3MuslimTheme(
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
