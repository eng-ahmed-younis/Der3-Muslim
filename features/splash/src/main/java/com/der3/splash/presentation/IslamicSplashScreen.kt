package com.der3.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun IslamicSplashRoute() {

    val viewModel = hiltViewModel<IslamicSplashViewModel>()

}



@Composable
fun IslamicSplashScreen() {



}




@Composable
@Preview(showBackground = true, showSystemUi = true)
fun IslamicSplashScreenPreview() {

    Der3MuslimTheme {
        IslamicSplashScreen()
    }
}


// 3 second delay
LaunchedEffect(Unit) {
    delay(3000)
    onNavigateToHome()
}
