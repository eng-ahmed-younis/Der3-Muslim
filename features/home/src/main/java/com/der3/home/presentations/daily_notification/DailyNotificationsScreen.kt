package com.der3.home.presentations.daily_notification

import Der3TopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.home.domain.DailyNotificationItem
import com.der3.home.presentations.daily_notification.components.DailyNotificationCard
import com.der3.ui.themes.AppColors
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.presentations.daily_notification.components.AddCustomNotificationButton
import com.der3.home.presentations.daily_notification.components.DailyNotificationsHeader
import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsIntent
import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.PalletColors
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun DailyNotificationsRoute(
    onNavigate: (Screens) -> Unit
){

    val viewModel = hiltViewModel<DailyNotificationsViewModel>()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

            }
        }.launchIn(scope)
    }


    DailyNotificationsScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )



    ShiftSystemBarStyle(
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        isEdgeToEdgeEnabled = true,
        useDarkStatusBarIcons = true,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

}





@Composable
fun DailyNotificationsScreen(
    state: DailyNotificationsState,
    onIntent: (DailyNotificationsIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {

        Der3TopAppBar(
            backgroundColor = AppColors.gray50,
            title = stringResource(id = R.string.daily_notifications_title),
            onBackClick = {
                onIntent(DailyNotificationsIntent.NavigateBack)
            },
            actionIcon = Icons.Default.Settings,
            onActionClick = {}
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                DailyNotificationsHeader(
                    title = stringResource(R.string.daily_notifications_title),
                    subtitle = stringResource(R.string.daily_notifications_header_subtitle),
                    icon = Icons.Default.Notifications,
                    iconColor = AppColors.green800,
                    iconBgColor = AppColors.green50,
                )
            }

            items(state.notifications, key = { it.id }) { item ->
                DailyNotificationCard(
                    item = item,
                    onToggle = { enabled ->
                        //onToggle(item.id, enabled)
                    },
                    onEditTime = {
                      //  onEditTime(item.id)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                AddCustomNotificationButton(
                    modifier = Modifier,
                    text = stringResource(R.string.daily_notifications_add_custom),
                    onClick = {
                        onIntent(DailyNotificationsIntent.NavigateToAddCustomReminder)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DailyNotificationsScreenPreview() {
    val mockItems = listOf(
        DailyNotificationItem(
            id = "1",
            title = "أذكار الصباح",
            subtitle = "تبدأ عند شروق الشمس",
            icon = Icons.Default.WbSunny,
            iconBg = PalletColors.iconBackgroundColors.random(),
            timeText = "06:00 AM",
            enabled = true
        ),
        DailyNotificationItem(
            id = "2",
            title = "أذكار المساء",
            subtitle = "تبدأ عند غروب الشمس",
            icon = Icons.Default.NightsStay,
            iconBg = PalletColors.iconBackgroundColors.random(),
            timeText = "06:15 PM",
            enabled = false
        )
    )

    Der3MuslimTheme() {

        ShiftSystemBarStyle(
            statusBarColor = AppColors.gray50,
            isStatusBarVisible = true,
            isEdgeToEdgeEnabled = true,
            useDarkStatusBarIcons = true,
            navigationBarColor = AppColors.gray50,
            useDarkNavigationBarIcons = false
        )


        DailyNotificationsScreen(
            state = DailyNotificationsState(
                notifications = mockItems
            ),
            onIntent = {}
        )
    }
}
