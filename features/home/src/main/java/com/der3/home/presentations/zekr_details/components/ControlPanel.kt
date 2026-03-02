import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    onFavorite: () -> Unit,
    onPlay: () -> Unit,
    onReset: () -> Unit,
    onShare: () -> Unit,
    onVolume: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.white)
            .padding(vertical = 30.dp),
    ){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(AppColors.white)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onFavorite,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(AppColors.green50)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = AppColors.green800
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.green50)
                    .height(58.dp)
                    .padding(horizontal = 20.dp)
                // .weight(.5f)
            ) {


                IconButton(
                    onClick = onReset
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = AppColors.green800
                    )
                }

                IconButton(
                    onClick = onPlay,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(AppColors.green700)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = AppColors.white
                    )
                }


                IconButton(onClick = onVolume) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Volume",
                        tint = AppColors.green800
                    )
                }


            }

            IconButton(
                onClick = onShare,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(AppColors.green50)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = AppColors.green800
                )
            }

        }
     //   Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ControlPanelPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ControlPanel(
            onFavorite = {},
            onPlay = {},
            onReset = {},
            onShare = {},
            onVolume = {}
        )
    }
}