package com.der3.splash.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.splash.presentation.mvi.IslamicSplashIntent
import com.der3.splash.presentation.mvi.IslamicSplashState
import com.der3.ui.R
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun IslamicSplashRoute(
    onNavigate: (Screens) -> Unit
) {
    if (LocalInspectionMode.current) {
        IslamicSplashScreen(
            state = IslamicSplashState(),
            onIntent = {}
        )
        return
    }

    val viewModel: IslamicSplashViewModel = hiltViewModel()

    val viewState = viewModel.viewState
    val sendIntent = viewModel::onIntent


    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

            }
        }.launchIn(scope)
    }


    IslamicSplashScreen(
        state = viewState,
        onIntent = sendIntent
    )
}

@Composable
fun IslamicSplashScreen(
    state: IslamicSplashState,
    onIntent: (IslamicSplashIntent) -> Unit,
) {
    val backgroundColor = if (isDarkTheme) AppColors.screenBackground else AppColors.green900

    ShiftSystemBarStyle(
        statusBarColor = backgroundColor,
        isStatusBarVisible = true,
        isEdgeToEdgeEnabled = true,
        useDarkStatusBarIcons = false,
        navigationBarColor = backgroundColor,
        useDarkNavigationBarIcons = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.splash_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(400.dp)
                .offset(y = (-70).dp),

            )


        Column(
            modifier = Modifier
                .offset(y = (70).dp),
        ) {


            Text(
                text = stringResource(R.string.der3_muslim_title_Capitalize),
                modifier = Modifier
                    .fillMaxWidth(),
                color = if (isDarkTheme) AppColors.gray900Text else AppColors.white,
                fontSize = 30.sp,
                letterSpacing = 1.6.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "درع المسلم",
                modifier = Modifier
                    .fillMaxWidth(),
                color = AppColors.gold500,
                fontSize = 20.sp,
                letterSpacing = .6.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Composable
fun IslamicSplashScreenLightPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.LIGHT
    ) {
        IslamicSplashScreen(
            state = IslamicSplashState(),
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
fun IslamicSplashScreenDarkPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.DARK
    ) {
        IslamicSplashScreen(
            state = IslamicSplashState(),
            onIntent = {}
        )
    }
}
