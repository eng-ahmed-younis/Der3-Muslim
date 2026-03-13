package com.der3.home.presentations.masbaha.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

enum class TargetGoalType {
    UNLIMITED,
    SPECIFIC
}

@Composable
fun TargetDialog(
    currentTarget: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedGoalType by remember {
        mutableStateOf(if (currentTarget == -1) TargetGoalType.UNLIMITED else TargetGoalType.SPECIFIC)
    }

    var text by remember {
        mutableStateOf(if (currentTarget == -1) "" else currentTarget.toString())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.white,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = stringResource(id = R.string.edit_target),
                color = AppColors.green900,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                // Unlimited Option
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = selectedGoalType == TargetGoalType.UNLIMITED,
                            onClick = { selectedGoalType = TargetGoalType.UNLIMITED },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGoalType == TargetGoalType.UNLIMITED,
                        onClick = { selectedGoalType = TargetGoalType.UNLIMITED },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppColors.green800,
                            unselectedColor = AppColors.gray300
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.target_unlimited),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedGoalType == TargetGoalType.UNLIMITED) AppColors.green900 else AppColors.gray500,
                        fontWeight = if (selectedGoalType == TargetGoalType.UNLIMITED) FontWeight.Bold else FontWeight.Normal
                    )
                }

                // Custom Count Option
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = selectedGoalType == TargetGoalType.SPECIFIC,
                            onClick = { selectedGoalType = TargetGoalType.SPECIFIC },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGoalType == TargetGoalType.SPECIFIC,
                        onClick = { selectedGoalType = TargetGoalType.SPECIFIC },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppColors.green800,
                            unselectedColor = AppColors.gray300
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.target_label),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedGoalType == TargetGoalType.SPECIFIC) AppColors.green900 else AppColors.gray500,
                        fontWeight = if (selectedGoalType == TargetGoalType.SPECIFIC) FontWeight.Bold else FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            text = it
                            selectedGoalType = TargetGoalType.SPECIFIC
                        }
                    },
                    enabled = selectedGoalType == TargetGoalType.SPECIFIC,
                    label = { Text(stringResource(id = R.string.new_target)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.green800,
                        unfocusedBorderColor = AppColors.gray200,
                        focusedLabelColor = AppColors.green800,
                        unfocusedLabelColor = AppColors.gray500,
                        disabledBorderColor = AppColors.gray50,
                        disabledLabelColor = AppColors.gray200,
                        cursorColor = AppColors.green800
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedGoalType == TargetGoalType.UNLIMITED) {
                        onConfirm(-1)
                    } else {
                        text.toIntOrNull()?.let { onConfirm(it) }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.green800)
            ) {
                Text(
                    text = stringResource(id = android.R.string.ok),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = android.R.string.cancel),
                    color = AppColors.gray400
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TargetDialogPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        TargetDialog(
            currentTarget = 33,
            onDismiss = {},
            onConfirm = {}
        )
    }
}
