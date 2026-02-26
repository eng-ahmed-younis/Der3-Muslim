import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.der3.navigation.builder.mainNavigation
import com.der3.navigation.builder.sideMenuNavigation
import com.der3.screens.Der3NavigationRoute
import com.der3.splash.presentation.IslamicSplashRoute

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Der3NavigationRoute.SplashScreen,
        modifier = modifier
    ) {
        mainNavigation(rootNavController = navController)
        sideMenuNavigation(rootNavController = navController)
    }
}