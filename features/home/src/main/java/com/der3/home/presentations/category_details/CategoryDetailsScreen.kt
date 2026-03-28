package com.der3.home.presentations.category_details

import com.der3.ui.components.ReadingInfoCard
import com.der3.home.presentations.category_details.components.ZekrCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.der3.shared.params.CategoryDetailsParams
import com.der3.home.di.factory.CategoryDetailsViewModelFactory
import com.der3.home.domain.model.ZekrUiModel
import com.der3.home.presentations.category_details.mvi.CategoryDetailsIntent
import com.der3.home.presentations.category_details.mvi.CategoryDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import com.der3.utils.estimateReadingTimeInMinutes
import com.der3.utils.toMinutesText
import com.der3.utils.toZekrCountTex
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun CategoryDetailsRoute(
    params: CategoryDetailsParams,
    onNavigate: (Screens) -> Unit,
){
    val viewModel: CategoryDetailsViewModel = hiltViewModel<CategoryDetailsViewModel, CategoryDetailsViewModelFactory> { factory ->
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


    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.screenBackground,
        useDarkNavigationBarIcons = false
    )

    CategoryDetailsScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )

}



@Composable
fun CategoryDetailsScreen(
    state: CategoryDetailsState,
    onIntent: (CategoryDetailsIntent) -> Unit
) {

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground,)
    ) {

        Der3TopAppBar(
            title = state.categoryTitle,
            backgroundColor = AppColors.screenBackground,
            onBackClick = { onIntent(CategoryDetailsIntent.OnBackClick) },
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
                    onFavoriteClick = {
                        onIntent(CategoryDetailsIntent.OnFavoriteClick(zekr))
                    },
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Light Mode"
)
@Composable
private fun CategoryDetailsScreenLightPreview() {

     val mockAzkar = listOf(
         ZekrUiModel(
             id = 1,
             text = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ",
             audioPath = "",
             repeatCount = 1,
             isFavorite = false,
             isBookmarked = false
         ),
         ZekrUiModel(
             id = 2,
             text = "اللّهُـمَّ بِكَ أَصْـبَحْنا وَبِكَ أَمْسَـينا",
             audioPath = "",
             repeatCount = 1,
             isFavorite = false,
             isBookmarked = false
         ),

     )

    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CategoryDetailsScreen(
            state = CategoryDetailsState(
                azkarItems = mockAzkar,
                categoryTitle = "أذكار الصباح"
            ),
            onIntent = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dark Mode",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CategoryDetailsScreenDarkPreview() {

    val mockAzkar = listOf(
        ZekrUiModel(
            id = 1,
            text = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ",
            audioPath = "",
            repeatCount = 1,
            isFavorite = false,
            isBookmarked = false
        ),
        ZekrUiModel(
            id = 2,
            text = "اللّهُـمَّ بِكَ أَصْـبَحْنا وَبِكَ أَمْسَـينا",
            audioPath = "",
            repeatCount = 1,
            isFavorite = false,
            isBookmarked = false
        ),

    )

    Der3MuslimTheme(
        style = com.der3.model.AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CategoryDetailsScreen(
            state = CategoryDetailsState(
                azkarItems = mockAzkar,
                categoryTitle = "أذكار الصباح"
            ),
            onIntent = {}
        )
    }
}