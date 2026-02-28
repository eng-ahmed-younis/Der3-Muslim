package com.der3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun SwitchCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: ImageVector
) {

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.white)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(AppColors.green50, CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                     icon,
                    modifier = Modifier.graphicsLayer {
                        // This flips the icon horizontally
                        rotationY = 180f
                    },
                    contentDescription = null,
                    tint = AppColors.green800
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, color = AppColors.gray500)
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )

        }
    }
}


@Preview(showBackground = true, name = "Switch Card Preview")
@Composable
private fun SwitchCardPreview() {
    var isChecked by remember { mutableStateOf(true) }

    Der3MuslimTheme(
        language = Locale("ar")
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.gray50)
                .padding(16.dp)
        ) {
            SwitchCard(
                title = "تنبيهات الأذكار",
                subtitle = "تفعيل التنبيهات التلقائية",
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                icon = Icons.Rounded.Person
              //  icon = Icons.AutoMirrored.Filled.VolumeUp
            )
        }
    }
}
