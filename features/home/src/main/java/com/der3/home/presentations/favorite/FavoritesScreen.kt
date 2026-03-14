package com.der3.home.presentations.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
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
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
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
                color = AppColors.gray50
            )
    ) {
        Der3TopAppBar(
            title = stringResource(R.string.favorites_title),
            showBackButton = false,
            backgroundColor = AppColors.gray50
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
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.favorites_search_placeholder),
                            color = AppColors.gray500,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp
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
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(AppColors.green400.copy(alpha = 0.3f))
                                    .clickable { onIntent(FavoritesIntent.OnSearchQueryChanged("")) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = AppColors.gray500,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(4.dp),
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = AppColors.green400.copy(alpha = 0.3f),
                        unfocusedContainerColor = AppColors.green400.copy(alpha = 0.3f)
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
                        FavoriteZekrCard(
                            zekr = zekr,
                            onRemove = { onIntent(FavoritesIntent.RemoveFromFavorite(zekr.id)) },
                            onPlay = { onIntent(FavoritesIntent.ToggleAudio(zekr.audioPath)) },
                            onClick = { onIntent(FavoritesIntent.OnZekrClick(zekr.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoritesScreenPreview() {
    Der3MuslimTheme(
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
