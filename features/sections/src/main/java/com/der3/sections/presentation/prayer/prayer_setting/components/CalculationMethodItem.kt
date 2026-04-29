package com.der3.sections.presentation.prayer.prayer_setting.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.sections.presentation.prayer.prayer_setting.mvi.CalculationMethodUi
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

@Composable
fun CalculationMethodItem(
    method: CalculationMethodUi,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val isDark = isDarkTheme
    val selectionColor = if (isDark) AppColors.gold500 else AppColors.green800

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardColor
        ),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, selectionColor) else null
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isSelected) selectionColor else AppColors.gray300
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
                if (isSelected) {
                    Text(
                        text = stringResource(id = com.der3.ui.R.string.currently_adopted),
                        color = AppColors.gold500,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = method.name,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) AppColors.green900 else AppColors.gray500,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(name = "Light Mode Selected", showBackground = true)
@Composable
private fun CalculationMethodItemLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CalculationMethodItem(
                method = CalculationMethodUi(id = 2, name = "رابطة العالم الإسلامي"),
                isSelected = true,
                onSelect = {}
            )
        }
    }
}

@Preview(name = "Dark Mode Unselected", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalculationMethodItemDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CalculationMethodItem(
                method = CalculationMethodUi(id = 2, name = "جامعة أم القرى"),
                isSelected = false,
                onSelect = {}
            )
        }
    }
}
