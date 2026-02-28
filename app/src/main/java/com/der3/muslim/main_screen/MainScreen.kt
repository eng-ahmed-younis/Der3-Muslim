package com.der3.muslim.main_screen

import MainNavHost
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.der3.muslim.main_screen.bottom_bar.Der3BottomBar
import com.der3.muslim.main_screen.bottom_bar.bottomTabs
import com.der3.muslim.main_screen.drawer.AzkarDrawer
import com.der3.muslim.main_screen.drawer.model.drawerItems
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.screens.Der3NavigationRoute
import com.der3.ui.themes.AppColors
import com.der3.utils.LocalDrawerState
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val drawerState = LocalDrawerState.current
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    

    val showBottomBar = when (currentRoute) {
         Der3NavigationRoute.HomeScreen::class.qualifiedName,
         Der3NavigationRoute.FavouriteScreen::class.qualifiedName,
         Der3NavigationRoute.TasbeehScreen::class.qualifiedName,
         Der3NavigationRoute.SectionScreen::class.qualifiedName -> true
        else -> false
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AzkarDrawer(
                currentRoute = currentRoute.orEmpty(),
                items = drawerItems,
                onItemClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigateTo(route)
                }
            )
        }
    ) {
        Scaffold(
            containerColor = AppColors.gray50,
         /*   topBar = {
                if (showBottomBar) {
                    TopAppBar(
                        title = { Text("تطبيق أذكار") },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(Icons.Default.Menu, null)
                            }
                        }
                    )
                }
            },*/
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(400)
                    )
                ) {
                    Der3BottomBar(
                        currentRoute = currentRoute ?: "",
                        bottomTabs = bottomTabs,
                        onTabClick = { route -> navController.navigateTo(route) }
                    )
                }
            }
        ) { padding ->
            MainNavHost(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}