package com.der3.home.presentations.zekr_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    audioProgress: Float = 0f,
    isFavorite: Boolean = false,
    onFavorite: () -> Unit,
    onPlay: () -> Unit,
    onReset: () -> Unit,
    onShare: () -> Unit,
    onVolume: () -> Unit
) {
    val cornerRadiusValue = 28.dp
    val strokeWidth = 3.dp
    val colors = AppColors
    val trackColor = colors.green100.copy(alpha = 0.4f)
    val progressColor = colors.green700
    val iconTint = colors.green800

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(cornerRadiusValue),
                spotColor = colors.green900.copy(alpha = 0.2f)
            )
            .background(colors.white, RoundedCornerShape(cornerRadiusValue))
            .drawWithCache {
                val strokePx = strokeWidth.toPx()
                val w = size.width
                val h = size.height
                val r = cornerRadiusValue.toPx() - strokePx / 2
                val rect = Rect(
                    left = strokePx / 2,
                    top = strokePx / 2,
                    right = w - strokePx / 2,
                    bottom = h - strokePx / 2
                )

                val path = Path().apply {
                    moveTo(w / 2f, rect.top)
                    // Top right segment
                    lineTo(rect.right - r, rect.top)
                    arcTo(
                        rect = Rect(rect.right - 2 * r, rect.top, rect.right, rect.top + 2 * r),
                        startAngleDegrees = -90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Right side
                    lineTo(rect.right, rect.bottom - r)
                    arcTo(
                        rect = Rect(rect.right - 2 * r, rect.bottom - 2 * r, rect.right, rect.bottom),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Bottom side
                    lineTo(rect.left + r, rect.bottom)
                    arcTo(
                        rect = Rect(rect.left, rect.bottom - 2 * r, rect.left + 2 * r, rect.bottom),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Left side
                    lineTo(rect.left, rect.top + r)
                    arcTo(
                        rect = Rect(rect.left, rect.top, rect.left + 2 * r, rect.top + 2 * r),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Back to center top
                    lineTo(w / 2f, rect.top)
                    close()
                }
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)

                onDrawBehind {
                    if (isPlaying || audioProgress > 0f) {
                        // Progress Track (Background Border)
                        drawPath(
                            path = path,
                            color = trackColor,
                            style = Stroke(width = strokePx, cap = StrokeCap.Round)
                        )

                        // Active Progress Border (Expands symmetrically from top center)
                        if (audioProgress > 0f) {
                            val totalLength = pathMeasure.length
                            val halfProgressLength = (totalLength / 2f) * audioProgress

                            // Right side (Clockwise)
                            val rightPath = Path()
                            pathMeasure.getSegment(0f, halfProgressLength, rightPath, true)
                            drawPath(
                                path = rightPath,
                                color = progressColor,
                                style = Stroke(width = strokePx, cap = StrokeCap.Round)
                            )

                            // Left side (Counter-Clockwise)
                            val leftPath = Path()
                            pathMeasure.getSegment(
                                startDistance = totalLength - halfProgressLength,
                                stopDistance = totalLength,
                                destination = leftPath,
                                startWithMoveTo = true
                            )
                            drawPath(
                                path = leftPath,
                                color = progressColor,
                                style = Stroke(width = strokePx, cap = StrokeCap.Round)
                            )
                        }
                    }
                }
            }
            .height(88.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Secondary Actions Left
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Primary Play Button
            Surface(
                onClick = onPlay,
                shape = CircleShape,
                color = progressColor,
                modifier = Modifier.size(56.dp),
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Secondary Actions Right
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onReset) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onVolume) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Volume",
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ControlPanelPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            ControlPanel(
                audioProgress = 0.5f,
                onFavorite = {},
                onPlay = {},
                onReset = {},
                onShare = {},
                onVolume = {}
            )
        }
    }
}
