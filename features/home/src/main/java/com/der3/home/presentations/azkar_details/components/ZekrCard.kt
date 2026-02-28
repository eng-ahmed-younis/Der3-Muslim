import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.home.domain.ZekrUiModel
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun ZekrCard(
    zekr: ZekrUiModel,
    onPlay: () -> Unit,
    onFavorite: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.white),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPlay) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(
                                AppColors.green50,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = AppColors.green800
                        )
                    }
                }

                Spacer(Modifier.width(10.dp))

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    text = zekr.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.gray900Text
                )

            }

            Spacer(Modifier.height(12.dp))

            HorizontalDivider(color = AppColors.gray100) // Updated from Divider

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(8.dp))

                RepeatBadge(count = zekr.repeatCount)

                Spacer(Modifier.weight(1f))

                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (zekr.isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (zekr.isFavorite)
                            Color.Red
                        else
                            AppColors.gray500
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ZekrCardPreview() {
    val sampleZekr = ZekrUiModel(
        id = "1",
        text = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ",
        translation = "We have reached the morning and at this very time unto Allah belongs all sovereignty.",
        source = "رواه مسلم",
        repeatCount = 3,
        isFavorite = true
    )

    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            ZekrCard(
                zekr = sampleZekr,
                onPlay = {},
                onFavorite = {}
            )
        }
    }
}