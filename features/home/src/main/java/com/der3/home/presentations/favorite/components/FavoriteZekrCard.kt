package com.der3.home.presentations.favorite.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.home.domain.model.ZekrUiModel
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteZekrCard(
    modifier: Modifier = Modifier,
    zekr: ZekrUiModel,
    isPlaying: Boolean = false,
    onRemove: () -> Unit,
    onPlay: () -> Unit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "card_scale")

    val infiniteTransition = rememberInfiniteTransition(label = "playback_pulsate")
    val pulsateScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulsate"
    )

    val cardBackgroundColor by animateColorAsState(
        targetValue = if (isPlaying) AppColors.green25.copy(alpha = 0.5f) else AppColors.cardColor,
        label = "card_bg_color"
    )

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onRemove()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> AppColors.gold600
                    else -> Color.Transparent
                },
                label = "dismiss_background_color"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
                    .background(color, RoundedCornerShape(24.dp))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = AppColors.gold600
                )
            }
        },
        modifier = modifier
            .scale(scale)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isPlaying) 4.dp else 2.dp),
            border = if (com.der3.ui.themes.isDarkTheme) androidx.compose.foundation.BorderStroke(
                1.dp,
                AppColors.green700.copy(alpha = 0.2f)
            ) else null
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .background(AppColors.green25, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        val countText = pluralStringResource(
                            id = R.plurals.times_count,
                            count = zekr.repeatCount,
                            zekr.repeatCount
                        )
                        Text(
                            text = stringResource(id = R.string.repeat_label, countText),
                            color = AppColors.green800,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.remove_from_favorites),
                            tint = AppColors.gold500
                        )
                    }
                }
                zekr.categoryName?.let {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = zekr.categoryName,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.gray900Text
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = zekr.text,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = AppColors.gray500
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .scale(if (isPlaying) pulsateScale else 1f)
                            .background(
                                color = if (isPlaying) AppColors.green900 else AppColors.green800,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onPlay() }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 8.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = stringResource(id = if (isPlaying) R.string.pause_audio else R.string.play_audio),
                            tint = AppColors.white,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = if (isPlaying) R.string.pause_audio else R.string.play_audio),
                            color = AppColors.white,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.swipe_to_delete),
                        color = AppColors.gray400,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun FavoriteZekrCardLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            FavoriteZekrCard(
                zekr = ZekrUiModel(
                    id = 1,
                    text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ وَالحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شريكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    audioPath = "",
                    repeatCount = 3,
                    isFavorite = true
                ),
                onRemove = {},
                onPlay = {},
                onClick = {}
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun FavoriteZekrCardDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            FavoriteZekrCard(
                zekr = ZekrUiModel(
                    id = 1,
                    text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ وَالحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شريكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    audioPath = "",
                    repeatCount = 3,
                    isFavorite = true
                ),
                onRemove = {},
                onPlay = {},
                onClick = {}
            )
        }
    }
}
