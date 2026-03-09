package com.der3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
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
fun VolumeBottomSheet(
    isVisible: Boolean,
    currentVolume: Float,
    onDismiss: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onSave: (Float) -> Unit
) {
    var sliderPosition by remember {
        mutableFloatStateOf(currentVolume)
    }

    LaunchedEffect(currentVolume) {
        sliderPosition = currentVolume
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
                            text = "مستوى الصوت",
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
                            Icon(
                                imageVector = Icons.Default.VolumeDown,
                                contentDescription = null,
                                tint = AppColors.gray900Text
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${(sliderPosition * 100).toInt()}%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.green800
                                )

                                Slider(
                                    value = sliderPosition,
                                    onValueChange = {
                                        sliderPosition = it
                                        onVolumeChange(it)
                                    },
                                    valueRange = 0f..1f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = AppColors.green700,
                                        activeTrackColor = AppColors.green500,
                                        inactiveTrackColor = AppColors.green100
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = null,
                                tint = AppColors.gray900Text
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
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VolumeBottomSheetPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        VolumeBottomSheet(
            isVisible = true,
            currentVolume = 0.5f,
            onDismiss = {},
            onVolumeChange = {},
            onSave = {}
        )
    }
}
