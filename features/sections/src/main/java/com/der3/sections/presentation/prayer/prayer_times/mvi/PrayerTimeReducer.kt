package com.der3.sections.presentation.prayer.prayer_times.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class PrayerTimeReducer @Inject constructor() : Reducer<PrayerTimeAction, PrayerTimeState> {
    override fun reduce(action: PrayerTimeAction, state: PrayerTimeState): PrayerTimeState {
        return when (action) {
            is PrayerTimeAction.OnPrayerTimesLoaded -> state.copy(
                locationName = action.location,
                hijriDate = action.hijriDate,
                gregorianDate = action.gregorianDate,
                prayerTimes = action.prayerTimes,
                nextPrayer = action.prayerTimes.find { it.isNext },
                isLoading = false
            )
            is PrayerTimeAction.OnNotificationToggled -> {
                val updatedList = state.prayerTimes.map {
                    if (it.type == action.prayerType) it.copy(notificationEnabled = !it.notificationEnabled)
                    else it
                }
                state.copy(
                    prayerTimes = updatedList,
                    nextPrayer = if (state.nextPrayer?.type == action.prayerType) 
                        state.nextPrayer.copy(notificationEnabled = !state.nextPrayer.notificationEnabled)
                    else state.nextPrayer
                )
            }
            is PrayerTimeAction.OnLoading -> state.copy(isLoading = action.isLoading)
            is PrayerTimeAction.OnError -> state.copy(error = action.message, isLoading = false)
            is PrayerTimeAction.ClearError -> state.copy(error = null)
        //    is PrayerTimeAction.OnCalculationMethodsLoaded -> state.copy(calculationMethods = action.methods)
            is PrayerTimeAction.OnMethodChanged -> state.copy(selectedMethodId = action.methodId)
            is PrayerTimeAction.OnSchoolChanged -> state.copy(selectedSchoolId = action.schoolId)
            is PrayerTimeAction.OnTimeFormatChanged -> state.copy(is24HourFormat = action.is24Hour)
            is PrayerTimeAction.OnLocationChanged -> state.copy(
                latitude = action.lat,
                longitude = action.lng,
                locationName = action.locationName
            )
            is PrayerTimeAction.OnMonthlyCalendarLoaded -> state.copy(monthlyCalendar = action.calendar)
        }
    }
}
