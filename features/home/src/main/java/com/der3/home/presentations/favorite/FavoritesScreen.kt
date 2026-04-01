package com.der3.home.presentations.favorite

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.domain.model.ZekrUiModel
import com.der3.home.presentations.favorite.components.EmptyFavoritesView
import com.der3.home.presentations.favorite.components.FavoriteZekrCard
import com.der3.home.presentations.favorite.mvi.FavoritesIntent
import com.der3.home.presentations.favorite.mvi.FavoritesState
import com.der3.model.AppStyle
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun FavoritesRoute(
    onNavigate: (Screens) -> Unit
) {
    val viewModel: FavoritesViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    showErrorDialog = true
                }

                else -> {}
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            viewModel.onIntent(FavoritesIntent.LoadFavorites)
            showErrorDialog = false
        },
        onDismiss = { showErrorDialog = false }
    )

    LoadingDialog(visible = viewModel.viewState.isLoading)


    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.screenBackground,
        useDarkNavigationBarIcons = false
    )


    FavoritesScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onIntent: (FavoritesIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = AppColors.screenBackground
            )
    ) {
        Der3TopAppBar(
            title = stringResource(R.string.favorites_title),
            showBackButton = false,
            backgroundColor = AppColors.screenBackground,
            trailingContent = {
                var showMenu by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = AppColors.green800
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier
                            .background(AppColors.cardColor)
                            .width(180.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.recycle_bin_title),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = AppColors.green900
                                )
                            },
                            onClick = {
                                showMenu = false
                                onIntent(FavoritesIntent.OpenRecycleBin)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = AppColors.green800,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        )
                    }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Search Bar
            if (state.favorites.isNotEmpty()) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { onIntent(FavoritesIntent.OnSearchQueryChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo))
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.favorites_search_placeholder),
                            color = AppColors.gray400,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.cairo))
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = AppColors.green800
                        )
                    },
                    trailingIcon = {
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onIntent(FavoritesIntent.OnSearchQueryChanged("")) }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = AppColors.gray500,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.gray100,
                        unfocusedBorderColor = AppColors.gray100,
                        focusedContainerColor = AppColors.cardColor,
                        unfocusedContainerColor = AppColors.cardColor,
                        cursorColor = AppColors.green800,
                        focusedTextColor = AppColors.green800,
                        unfocusedTextColor = AppColors.green800
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (state.favorites.isEmpty() && !state.isLoading) {
                EmptyFavoritesView(
                    onNavigateToAzkar = { onIntent(FavoritesIntent.NavigateToAzkar) }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.filteredFavorites, key = { it.id }) { zekr ->
                        val isCurrentPlaying = state.isPlaying && state.currentPlayingPath == zekr.audioPath

                        FavoriteZekrCard(
                            zekr = zekr,
                            isPlaying = isCurrentPlaying,
                            onRemove = { onIntent(FavoritesIntent.RemoveFromFavorite(zekr.id)) },
                            onPlay = { onIntent(FavoritesIntent.ToggleAudio(zekr.audioPath)) },
                            onClick = {
                                onIntent(
                                    FavoritesIntent.OnZekrClick(
                                        zekrId = zekr.id,
                                        categoryId = zekr.categoryId ?: -1
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Composable
fun FavoritesScreenLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        FavoritesScreen(
            state = FavoritesState(
                favorites = listOf(
                    ZekrUiModel(
                        id = 1,
                        text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ وَالحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                        audioPath = "",
                        repeatCount = 3,
                        isFavorite = true
                    ),
                    ZekrUiModel(
                        id = 2,
                        text = "لَا إِلَهَ إِلَّا اللهُ العَظِيمُ الحَلِيمُ، لَا إِلَهَ إِلَّا اللهُ رَبُّ العَرْشِ العَظِيمِ، لَا إِلَهَ إِلَّا اللهُ رَبُّ السَّمَواتِ وَرَبُّ الأَرْضِ وَرَبُّ العَرْشِ الكَرِيمِ",
                        audioPath = "",
                        repeatCount = 1,
                        isFavorite = true
                    )
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FavoritesScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = java.util.Locale.Builder().setLanguage("ar").build()
    ) {
        FavoritesScreen(
            state = FavoritesState(
                favorites = listOf(
                    ZekrUiModel(
                        id = 1,
                        text = "أَصْبَحْنَا وَأَصْبَحَ المُلْكُ للهِ وَالحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                        audioPath = "",
                        repeatCount = 3,
                        isFavorite = true
                    ),
                    ZekrUiModel(
                        id = 2,
                        text = "لَا إِلَهَ إِلَّا اللهُ العَظِيمُ الحَلِيمُ، لَا إِلَهَ إِلَّا اللهُ رَبُّ العَرْشِ العَظِيمِ، لَا إِلَهَ إِلَّا اللهُ رَبُّ السَّمَواتِ وَرَبُّ الأَرْضِ وَرَبُّ العَرْشِ الكَرِيمِ",
                        audioPath = "",
                        repeatCount = 1,
                        isFavorite = true
                    )
                )
            ),
            onIntent = {}
        )
    }
}
