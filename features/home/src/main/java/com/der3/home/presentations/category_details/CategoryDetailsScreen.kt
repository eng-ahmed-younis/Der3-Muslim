import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.data.params.CategoryDetailsParams
import com.der3.home.di.factory.CategoryDetailsViewModelFactory
import com.der3.home.domain.ZekrUiModel
import com.der3.home.presentations.category_details.CategoryDetailsViewModel
import com.der3.home.presentations.category_details.mvi.CategoryDetailsIntent
import com.der3.home.presentations.category_details.mvi.CategoryDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.components.ErrorDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import com.der3.utils.estimateReadingTimeInMinutes
import com.der3.utils.toMinutesText
import com.der3.utils.toZekrCountTex
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun AzkarDetailsRoute(
    params: CategoryDetailsParams,
    onNavigate: (Screens) -> Unit,
){


    val viewModel: CategoryDetailsViewModel =
        hiltViewModel<CategoryDetailsViewModel, CategoryDetailsViewModelFactory> { factory ->
            factory.create(params)
        }

    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiText?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    errorType = it.error
                    showErrorDialog = true
                }

            }
        }.launchIn(scope)
    }


    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {},
        onDismiss = {}
    )


    AzkarDetailsScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )

}



@Composable
fun AzkarDetailsScreen(
    state: CategoryDetailsState,
    onIntent: (CategoryDetailsIntent) -> Unit
) {

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {

        Der3TopAppBar(
            title = state.categoryTitle,
            backgroundColor = AppColors.gray50,
            onBackClick = { onIntent(CategoryDetailsIntent.OnBackClick) },
            actionIcon = Icons.Default.Share,
            onActionClick = {
                onIntent(CategoryDetailsIntent.OnShareClick(state.azkarItems.first()))
            }
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
                    countText = state.azkarItems.size.toZekrCountTex(),
                    durationText = estimateReadingTimeInMinutes(state.zakeTextList).toMinutesText()
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(state.azkarItems, key = { it.id }) { zekr ->
                ZekrCard(
                    zekr = zekr,
                    onPlayZekrSound = {},
                    onFavoriteClick = {},
                    onBookmarkClick = {},
                    onZekrClick = {
                        onIntent(CategoryDetailsIntent.OnZekrClick(zekr.id))
                    }

                  //  onPlay = { onPlay(zekr.id) },
                 //   onFavorite = { onFavorite(zekr.id) }
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
             sourceUrl = "",
             repeatCount = 1,
             isFavorite = false,
             isBookmarked = false
         ),
         ZekrUiModel(
             id = 2,
             text = "اللّهُـمَّ بِكَ أَصْـبَحْنا وَبِكَ أَمْسَـينا",
             sourceUrl = "",
             repeatCount = 1,
             isFavorite = false,
             isBookmarked = false
         ),

     )

    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AzkarDetailsScreen(
            state = CategoryDetailsState(
                azkarItems = mockAzkar
            ),
            onIntent = {}
        )
    }
}