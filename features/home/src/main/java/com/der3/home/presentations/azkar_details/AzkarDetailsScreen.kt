import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.home.domain.ZekrUiModel
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun AzkarDetailsScreen(
    azkar: List<ZekrUiModel>,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onSearch: () -> Unit,
    onPlay: (Int) -> Unit, // Changed to Int to match ZekrUiModel id type
    onFavorite: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {

        Der3TopAppBar(
            title = "أذكار الصباح",
            backgroundColor = AppColors.gray50,
            onBackClick = onBack,
            actionIcon = Icons.Default.Share,
            onActionClick = onShare
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                ReadingInfoCard(
                    title = "وقت القراءة",
                    description = "من طلوع الفجر إلى طلوع الشمس",
                    countText = "٢٤ ذكر",
                    durationText = "١٠ دقائق"
                )
            }

            items(azkar, key = { it.id }) { zekr ->
                ZekrCard(
                    zekr = zekr,
                    onPlay = { onPlay(zekr.id) },
                    onFavorite = { onFavorite(zekr.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AzkarDetailsScreenPreview() {
    val mockAzkar = listOf(
        ZekrUiModel(
            id = 1,
            text = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ",
            translation = "We have reached the morning...",
            source = "رواه مسلم",
            repeatCount = 3,
            isFavorite = true
        ),
        ZekrUiModel(
            id = 2,
            text = "اللّهُـمَّ بِكَ أَصْـبَحْنا وَبِكَ أَمْسَـينا",
            translation = "O Allah, by You we enter the morning...",
            source = "الترمذي",
            repeatCount = 1,
            isFavorite = false
        )
    )

    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AzkarDetailsScreen(
            azkar = mockAzkar,
            onBack = {},
            onShare = {},
            onSearch = {},
            onPlay = {},
            onFavorite = {}
        )
    }
}