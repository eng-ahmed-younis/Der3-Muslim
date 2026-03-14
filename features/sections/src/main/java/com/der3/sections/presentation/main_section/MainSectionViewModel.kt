package com.der3.sections.presentation.main_section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.lifecycle.viewModelScope
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.sections.presentation.main_section.mvi.MainSectionAction
import com.der3.sections.presentation.main_section.mvi.MainSectionIntent
import com.der3.sections.presentation.main_section.mvi.MainSectionReducer
import com.der3.sections.presentation.main_section.mvi.MainSectionState
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.sections.presentation.main_section.model.Section
import com.der3.sections.presentation.main_section.model.SectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSectionViewModel @Inject constructor(
    reducer: MainSectionReducer
) : MviBaseViewModel<MainSectionState, MainSectionAction, MainSectionIntent>(
    initialState = MainSectionState(),
    reducer = reducer
) {

    init {
        handleIntent(MainSectionIntent.LoadSections)
    }

    override fun handleIntent(intent: MainSectionIntent) {
        when (intent) {
            is MainSectionIntent.LoadSections -> loadSections()
            is MainSectionIntent.Retry -> loadSections()
            is MainSectionIntent.UpdateSearchQuery -> {
                onAction(MainSectionAction.OnSearchQueryUpdated(intent.query))
            }
            is MainSectionIntent.OnSectionClick -> {
                val route = when (intent.type) {
                    SectionType.ZIKR -> Der3NavigationRoute.AllAzkarCategoriesScreen
                    SectionType.TASBEH -> Der3NavigationRoute.TasbeehScreen
                    SectionType.FAVORITES -> Der3NavigationRoute.FavouriteScreen
                    SectionType.DAILY_NOTIFICATIONS -> Der3NavigationRoute.DailyNotificationsScreen
                    SectionType.PRAYERS_TIME -> Der3NavigationRoute.PrayerTimesScreen
                    SectionType.QIBLA -> Der3NavigationRoute.QiblaScreen
                    else -> null
                }
                route?.let { onEffect(MviEffect.Navigate(screen = route)) }
            }

        }
    }

    private fun loadSections() {
        viewModelScope.launch {
            onAction(MainSectionAction.OnSectionsLoaded(sections = createSections()))
        }
    }

    private fun createSections() : List<Section> {
        return mutableListOf<Section>().apply {
            add(Section(id = "1", type = SectionType.ZIKR, title =  "أذكار", subTitle = "حصن المسلم والأدعية", icon = Icons.Default.MenuBook))
            add(Section(id = "2", type = SectionType.QIBLA, title =  "القبلة", subTitle = "تحديد اتجاه الكعبة", icon = Icons.Default.Explore))
            add(Section(id = "3", type = SectionType.TASBEH, title =  "المسبحة", subTitle = "التسبيح الإلكتروني", icon = Icons.Default.Explore))
            add(Section(id = "4", type = SectionType.FAVORITES, title =  "المفضلة", subTitle = "محفوظاتك الخاصة", icon = Icons.Default.Bookmark))
            add(Section(id = "5", type = SectionType.DAILY_NOTIFICATIONS, title =  "التنبيهات", subTitle = "التنبيهات", icon = Icons.Default.Notifications))
            add(Section(id = "6", type = SectionType.PRAYERS_TIME, title =  "أوقات الصلاة", subTitle = "أوقات الصلاة", icon = Icons.Default.Schedule))

        }
    }
}
