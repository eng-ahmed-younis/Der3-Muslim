package com.der3.home.presentations.home_screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.shared.data.provider.ZekrCategoriesProvider
import com.der3.home.presentations.home_screen.components.CategoriesGrid
import com.der3.home.presentations.home_screen.components.DailyNotificationCard
import com.der3.home.presentations.home_screen.components.DailyZekrCard
import com.der3.home.presentations.home_screen.components.HomeTopHeader
import com.der3.home.presentations.home_screen.components.SectionHeader
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.model.AppStyle
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.models.LocalDrawerState
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    onNavigate: (Screens) -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiText?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }


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
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(HomeIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(HomeIntent.DismissError)
        }
    )

    HomeScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}


@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {

    val drawerState = LocalDrawerState.current
    val scope = rememberCoroutineScope()


    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
    ) {

        HomeTopHeader(
            modifier = Modifier,
            backgroundColor = AppColors.screenBackground,
            onDrawerClick = {
                if (drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                } else {
                    scope.launch { drawerState.open() }
                }
            },
            onNotificationClick = {
                onIntent(HomeIntent.NavigateToNotifications)
            }
        )

        // 🔹 Scrollable Content
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                DailyZekrCard()
            }

            item {
                SectionHeader(
                    title = stringResource(id = R.string.home_categories_title),
                    action = stringResource(id = R.string.home_view_all_action)
                ) {
                    onIntent(HomeIntent.NavigateToAllCategories)
                }
            }

            item {
                CategoriesGrid(
                    categories = state.homeAzkarCategory,
                    onCategoryClick = {
                        onIntent(HomeIntent.NavigateToAzkarDetails(it))
                    }
                )
            }

            item {
                DailyNotificationCard {
                    onIntent(HomeIntent.NavigateToDailyNotifications)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

    }
}

@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HomeScreen(
                state = HomeState(
                    isLoading = false,
                    homeAzkarCategory = ZekrCategoriesProvider.categories
                ),
                onIntent = {}
            )
        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HomeScreen(
                state = HomeState(
                    isLoading = false,
                    homeAzkarCategory = ZekrCategoriesProvider.categories
                ),
                onIntent = {}
            )
        }
    }
}
