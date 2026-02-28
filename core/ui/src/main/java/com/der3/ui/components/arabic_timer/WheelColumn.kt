package com.der3.ui.components.arabic_timer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.pad2
import com.der3.utils.wrap
import com.example.timepicker.LightGreen
import com.example.timepicker.SideValueColor
import kotlinx.coroutines.launch

@Composable
fun WheelColumn(
    value: Int,
    min: Int,
    max: Int,
    onValueChange: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    var dragAccumulator by remember { mutableFloatStateOf(0f) }

    val prev = wrap(value - 1, min, max)
    val next = wrap(value + 1, min, max)

    // Animate offset for slide effect
    val offsetY = remember { Animatable(0f) }

    fun animateChange(direction: Int) {
        val newVal = wrap(value + direction, min, max)
        onValueChange(newVal)
        scope.launch {
            offsetY.snapTo((-direction * 30).toFloat())
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 160, easing = FastOutSlowInEasing)
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Up arrow
        ArrowButton(up = true) { animateChange(-1) }

        // Prev value
        Text(
            text = prev.pad2(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = SideValueColor,
            modifier = Modifier.height(24.dp),
            textAlign = TextAlign.Center
        )

        // Center box
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(width = 88.dp, height = 76.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(LightGreen)
                .pointerInput(value) {
                    detectVerticalDragGestures(
                        onDragEnd = { dragAccumulator = 0f },
                        onDragCancel = { dragAccumulator = 0f },
                        onVerticalDrag = { _, dragAmount ->
                            dragAccumulator += dragAmount
                            if (dragAccumulator < -40f) {
                                animateChange(1)
                                dragAccumulator = 0f
                            } else if (dragAccumulator > 40f) {
                                animateChange(-1)
                                dragAccumulator = 0f
                            }
                        }
                    )
                }
        ) {
            Text(
                text = value.pad2(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.green800,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = offsetY.value.dp)
            )
        }

        // Next value
        Text(
            text = next.pad2(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = SideValueColor,
            modifier = Modifier.height(24.dp),
            textAlign = TextAlign.Center
        )

        // Down arrow
        ArrowButton(up = false) { animateChange(1) }
    }
}

@Preview(showBackground = true, name = "Wheel Column Preview")
@Composable
private fun WheelColumnPreview() {
    // Local state to make the preview interactive
    var currentValue by remember { mutableIntStateOf(5) }

    Der3MuslimTheme {
        Box(modifier = Modifier.padding(24.dp)) {
            WheelColumn(
                value = currentValue,
                min = 1,
                max = 12,
                onValueChange = { currentValue = it }
            )
        }
    }
}