package com.der3.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun ReminderNameField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    labelColor: Color = AppColors.blueGray400,
    iconAction: ImageVector = Icons.Default.Edit,
    borderColor: Color = AppColors.green100,
    iconActionColor: Color = AppColors.green400,
    backgroundColor: Color = Color.White,
    cursorColor: Color = AppColors.green400,
    textColor: Color = AppColors.gray900Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.cairo_bold))
        ),
        placeholder = {
            Text(
                text = label,
                color = labelColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.cairo_medium))
            )
        },

        modifier = modifier
            .fillMaxWidth(),

        trailingIcon = {
            Icon(
                imageVector = iconAction,
                contentDescription = null,
                tint = iconActionColor,
                modifier = Modifier.size(24.dp)
            )
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,

            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            disabledBorderColor = borderColor,

            cursorColor = cursorColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        )
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Reminder Name Field Preview"
)
@Composable
private fun ReminderNameFieldPreview() {
    // State management for the preview
    var textState by remember { mutableStateOf("") }

    Der3MuslimTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            ReminderNameField(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(16.dp),
                label = "مثال: أذكار الصباح",

                value = textState,
                onValueChange = { textState = it }
            )
        }

    }
}