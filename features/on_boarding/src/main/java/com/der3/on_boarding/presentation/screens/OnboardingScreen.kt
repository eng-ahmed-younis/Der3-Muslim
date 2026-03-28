package com.der3.on_boarding.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.model.AppStyle
import com.der3.mvi.MviEffect
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingIntent
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingState
import com.der3.on_boarding.presentation.screens.pages.GetStartedPage
import com.der3.on_boarding.presentation.screens.pages.KeyFeaturePage
import com.der3.on_boarding.presentation.screens.pages.WelcomePage
import com.der3.screens.Screens
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import com.der3.ui.themes.isStatusBarDark
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun OnBoardingRoute(
    onNavigate: (Screens) -> Unit
) {

    val viewModel = hiltViewModel<OnboardingViewModel>()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

            }
        }.launchIn(scope)
    }


    OnBoardingScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    onIntent: (OnBoardingIntent) -> Unit,
) {

    val isDark = isDarkTheme
    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        isEdgeToEdgeEnabled = !isStatusBarDark,
        useDarkStatusBarIcons = !isDark,
        navigationBarColor = AppColors.screenBackground,
        useDarkNavigationBarIcons = !isDark
    )


    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.totalPages }
    )

    // Sync state with pager when user swipes manually
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            onIntent(OnBoardingIntent.NextPage(pagerState.currentPage)) // or a dedicated SyncPage intent
        }
    }


    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(
                page = state.currentPage,
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }



    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        val pageOffset = (
                (pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction
                )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = AppColors.screenBackground
                )
                .graphicsLayer {
                    translationX = pageOffset * size.width
                }
        ) {
            when (page) {
                0 -> WelcomePage(
                    currentPage = state.currentPage,
                    totalPages = state.totalPages,
                    onSkip = {
                        onIntent(
                            OnBoardingIntent.SkipOnBoarding(page)
                        )
                    },
                    onNext = {
                        onIntent(
                            OnBoardingIntent.NextPage(page)
                        )
                    }
                )

                1 -> KeyFeaturePage(
                    currentPage = state.currentPage,
                    totalPages = state.totalPages,
                    onPrevious = {
                        onIntent(
                            OnBoardingIntent.PreviousPage(page)
                        )
                    },
                    onSkip = {
                        onIntent(
                            OnBoardingIntent.SkipOnBoarding(page)
                        )
                    },
                    onNext = {
                        onIntent(
                            OnBoardingIntent.NextPage(page)
                        )
                    }
                )

                2 -> GetStartedPage(
                    currentPage = state.currentPage,
                    totalPages = state.totalPages,
                    onBack = {
                        onIntent(
                            OnBoardingIntent.PreviousPage(page)
                        )
                    },
                    onFinish = {
                        onIntent(
                            OnBoardingIntent.CompleteOnBoarding(page)
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun OnBoardingScreenLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        OnBoardingScreen(
            state = OnBoardingState(currentPage = 0, totalPages = 3),
            onIntent = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun OnBoardingScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        OnBoardingScreen(
            state = OnBoardingState(currentPage = 0, totalPages = 3),
            onIntent = {}
        )
    }
}
