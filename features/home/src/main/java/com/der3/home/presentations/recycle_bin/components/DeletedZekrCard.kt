package com.der3.home.presentations.recycle_bin.components

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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.der3.home.domain.model.DeletedZekrUiModel
import com.der3.home.domain.model.ZekrUiModel
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun DeletedZekrCard(
    modifier: Modifier = Modifier,
    item: DeletedZekrUiModel,
    onRestore: () -> Unit,
    onDeletePermanently: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Column
                Column(
                    modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start
                ) {
                    item.zekr.categoryName?.let {
                        Text(
                            text = it,
                            color = AppColors.gold600,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                // Buttons Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onRestore,
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = AppColors.gold700.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = AppColors.gold700,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = onDeletePermanently,
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = AppColors.gold700.copy(alpha = 0.2f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = AppColors.gold700,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.zekr.text,
                color = AppColors.gray900Text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = AppColors.gray200.copy(alpha = 0.5f), thickness = 1.dp)

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom part - On the Left (End) in RTL
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.deleted_since, item.deletedSince),
                    color = AppColors.gold700,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = AppColors.gold700,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun DeletedZekrCardLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        DeletedZekrCard(
            item = DeletedZekrUiModel(
                zekr = ZekrUiModel(
                    id = 1,
                    text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ، وَالحَمْدُ للهِ",
                    categoryName = "أذكار الصباح",
                    repeatCount = 1,
                    audioPath = ""
                ), deletedSince = "يومين"
            ), onRestore = {}, onDeletePermanently = {})
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun DeletedZekrCardDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        DeletedZekrCard(
            item = DeletedZekrUiModel(
                zekr = ZekrUiModel(
                    id = 1,
                    text = " أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ، وَالحَمْدُ لله أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ، وَالحَمْدُ للهِ",
                    categoryName = "أذكار الصباح",
                    repeatCount = 1,
                    audioPath = ""
                ), deletedSince = "يومين"
            ), onRestore = {}, onDeletePermanently = {})
    }
}
