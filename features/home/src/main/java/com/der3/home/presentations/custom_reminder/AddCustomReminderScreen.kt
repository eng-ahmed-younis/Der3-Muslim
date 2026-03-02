package com.der3.home.presentations.custom_reminder

import Der3TopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.der3.ui.R
import com.der3.ui.components.DaysSelectorView
import com.der3.ui.components.ReminderNameField
import com.der3.ui.themes.AppColors
import com.der3.home.presentations.custom_reminder.arabic_timer.ArabicTimePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderIntent
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.components.SectionLabel
import com.der3.ui.components.SwitchCard
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.Der3MuslimTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale


@Composable
fun AddCustomReminderRoute(
    onNavigate: (Screens) -> Unit
) {

    val viewModel = hiltViewModel<AddCustomReminderViewModel>()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

            }
        }.launchIn(scope)
    }


    ShiftSystemBarStyle(
        statusBarColor = Color(0xFFF4F6F5),
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )



    AddCustomReminderScreen(
        state = viewModel.viewState,
        onNavigate = onNavigate,
        onIntent = viewModel::onIntent
    )

}

@Composable
fun AddCustomReminderScreen(
    state: AddCustomReminderState,
    onNavigate: (Screens) -> Unit,
    onIntent: (AddCustomReminderIntent) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {

        Der3TopAppBar(
            title = stringResource(id = R.string.daily_notifications_add_custom),
            backgroundColor = AppColors.gray50,
            titleColor = AppColors.green800,
            navigationIconColor = AppColors.green800,
            actionIconColor = AppColors.green800,
            onBackClick = {
                onNavigate(Screens.Back())
            }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // 🔹 اسم التنبيه
            item {
                SectionLabel(text = stringResource(id = R.string.reminder_name_label))
                ReminderNameField(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        )
                     //   .background(color = AppColors.white)
                        .fillMaxWidth(),
                    label = stringResource(id = R.string.reminder_name_hint),
                    value = state.reminderName,
                    onValueChange = {
                        onIntent(AddCustomReminderIntent.UpdateName(it))
                    }
                )
            }

            // 🔹 وقت التنبيه
            item {
                SectionLabel(text = stringResource(id = R.string.reminder_time_label))
                ArabicTimePicker(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        ),
                    initialHour = 3,
                    initialMinute = 30,
                    onTimeChanged = { hour, minute, amPm ->
                        onIntent(AddCustomReminderIntent.UpdateTime(hour, minute))
                    }
                )
            }

            // 🔹 تكرار التنبي
            item {
                SectionLabel(text = stringResource(id = R.string.reminder_repeat_label))
                DaysSelectorView(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        ),
                    selectedDays = setOf("س", "ج"),
                    onToggle = { selectedDay ->

                    }
                )
            }

            item {
                SwitchCard(
                    title = "صوت التنبيه",
                    subtitle = "نغمة افتراضية",
                    checked = false,
                    onCheckedChange = {},
                    icon = Icons.AutoMirrored.Filled.VolumeUp
                )
            }

            /*


                        // 🔹 صوت
                        item {
                            SwitchCard(
                                title = "صوت التنبيه",
                                subtitle = "نغمة افتراضية",
                                checked = soundEnabled,
                                onCheckedChange = onSoundToggle,
                                icon = Icons.Default.VolumeUp
                            )
                        }

                        // 🔹 اهتزاز
                        item {
                            SwitchCard(
                                title = "الاهتزاز",
                                subtitle = "مفعل عند الرنين",
                                checked = vibrationEnabled,
                                onCheckedChange = onVibrationToggle,
                                icon = Icons.Default.Vibration
                            )
                        }

                        item { Spacer(modifier = Modifier.height(8.dp)) }*/
        }

        // 🔹 زر الحفظ
      //  SaveButton(onSave)
    }
}






@Preview(showBackground = true, showSystemUi = true, name = "Add Custom Reminder")
@Composable
private fun AddCustomReminderScreenPreview() {
    // 🔹 Local state to make the preview interactive
    var name by remember { mutableStateOf("") }
    var hour by remember { mutableIntStateOf(3) }
    var minute by remember { mutableIntStateOf(30) }
    var isAm by remember { mutableStateOf(true) }
    var selectedDays by remember { mutableStateOf(setOf("س", "ج")) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(false) }

    Der3MuslimTheme(
        // Ensure RTL layout for Arabic
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AddCustomReminderScreen(
            state = AddCustomReminderState(),
            onNavigate = {},
            onIntent = {}
        )
    }
}

