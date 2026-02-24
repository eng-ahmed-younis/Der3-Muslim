package com.der3.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.ui.themes.Der3MuslimTheme


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

    IslamicSplashScreen()
}
