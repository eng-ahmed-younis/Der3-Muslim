import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.home.domain.model.ZekrUiModel
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun ZekrCard(
    modifier: Modifier = Modifier,
    zekr: ZekrUiModel,
    onPlayZekrSound: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onZekrClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable(onClick = onZekrClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.white),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onPlayZekrSound,
                    modifier = Modifier
                        .align(Alignment.Top)
                ) {
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

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(1.dp),
                color = AppColors.gray100
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(8.dp))

                RepeatBadge(count = zekr.repeatCount)

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .weight(0.5f),
                    verticalAlignment = Alignment
                        .CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        modifier = Modifier.size(30.dp),
                        onClick = onFavoriteClick
                    ) {
                        Icon(
                            imageVector = if (zekr.isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (zekr.isFavorite)
                                AppColors.green700
                            else
                                AppColors.gray500
                        )
                    }


                    // Means: “Mark this place so I can come back”
                    IconButton(
                        modifier = Modifier.size(30.dp),
                        onClick = onBookmarkClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmarks,
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                            tint = if (zekr.isBookmarked)
                                AppColors.green700
                            else
                                AppColors.gray500
                        )
                    }

                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ZekrCardPreview() {
    val sampleZekr =    ZekrUiModel(
        id = 2,
        text = "اللّهُـمَّ بِكَ أَصْـبَحْنا وَبِكَ أَمْسَـينا",
        audioPath = "",
        repeatCount = 1,
        isFavorite = false,
        isBookmarked = false
    )


    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            ZekrCard(
                zekr = sampleZekr,
                onPlayZekrSound = {},
                onFavoriteClick = {},
                onBookmarkClick = {}
            )
        }
    }
}