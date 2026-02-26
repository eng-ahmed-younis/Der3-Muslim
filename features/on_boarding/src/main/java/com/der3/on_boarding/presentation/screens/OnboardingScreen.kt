package com.der3.on_boarding.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.der3.mvi.MviEffect

import com.der3.on_boarding.presentation.screens.mvi.*
import com.der3.on_boarding.presentation.screens.pages.GetStartedPage
import com.der3.on_boarding.presentation.screens.pages.KeyFeaturePage
import com.der3.on_boarding.presentation.screens.pages.WelcomePage
import com.der3.screens.Screens
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

    ShiftSystemBarStyle(
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )


    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.totalPages }
    )


    // Sync pager with state
    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }

    HorizontalPager(state = pagerState) { page ->

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