import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.der3.navigation.builder.der3AppNavigation
import com.der3.navigation.builder.der3SectionNavigation
import com.der3.navigation.builder.sideMenuNavigation
import com.der3.screens.Der3NavigationRoute

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Der3NavigationRoute.SplashScreen,
        modifier = modifier,

    ) {
        der3AppNavigation(rootNavController = navController)
        der3SectionNavigation(rootNavController = navController)
        sideMenuNavigation(rootNavController = navController)
    }
}