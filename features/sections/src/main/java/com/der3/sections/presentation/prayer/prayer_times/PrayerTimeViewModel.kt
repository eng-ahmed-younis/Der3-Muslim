package com.der3.sections.presentation.prayer.prayer_times

import androidx.lifecycle.viewModelScope
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.sections.data.service.api.PrayerTimeService
import com.der3.sections.domain.model.CombinedPrayerInfo
import com.der3.sections.domain.model.PrayerStatus
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.sections.domain.use_case.prayer.GetCombinedPrayerInfoUseCase
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeAction
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeIntent
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeReducer
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeState
import com.der3.data_store.api.DataStoreRepository
import com.der3.sections.domain.model.PrayerDetails
import com.der3.sections.domain.model.PrayerType
import com.der3.utils.TimeFormatUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val getCombinedPrayerInfoUseCase: GetCombinedPrayerInfoUseCase,
    private val prayerTimeService: PrayerTimeService,
    private val dataStoreRepository: DataStoreRepository,
    reducer: PrayerTimeReducer,
) : MviBaseViewModel<PrayerTimeState, PrayerTimeAction, PrayerTimeIntent>(
    initialState = PrayerTimeState(),
    reducer = reducer
) {

    init {
        loadSettings()
        startCountdownTicker()
    }

    private fun loadSettings() {
        val lat = dataStoreRepository.latitude
        val lng = dataStoreRepository.longitude
        val locationName = dataStoreRepository.locationName ?: "مكة المكرمة"
        val is24h = dataStoreRepository.is24HourFormat
        
        onAction(PrayerTimeAction.OnTimeFormatChanged(is24h))
        onAction(PrayerTimeAction.OnLocationChanged(lat, lng, locationName))
        
        loadPrayerTimes(lat, lng, locationName)
    }

    private fun startCountdownTicker() {
        flow {
            while (true) {
                emit(Unit)
                delay(1000)
            }
        }.onEach {
            val state = viewState
            if (state.prayerTimes.isNotEmpty()) {
                val nextIndex = state.prayerTimes.indexOfFirst { it.isNext }
                if (nextIndex != -1) {
                    val next = state.prayerTimes[nextIndex]
                    onAction(PrayerTimeAction.OnPrayerTimesLoaded(
                        location = state.locationName,
                        hijriDate = state.hijriDate,
                        gregorianDate = state.gregorianDate,
                        prayerTimes = state.prayerTimes.toMutableList().apply {
                            this[nextIndex] = next.copy(remainingTime = next.calculateRemainingTimeFromTime())
                        }
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun PrayerDetails.calculateRemainingTimeFromTime(): String {
        val now = Calendar.getInstance()
        val isPm = time.contains("م") || time.contains("PM")
        val isAm = time.contains("ص") || time.contains("AM")
        
        val cleanTime = time.replace(" م", "").replace(" ص", "")
            .replace(" PM", "").replace(" AM", "")
            .trim()
            
        val timeParts = cleanTime.split(":")
        if (timeParts.size != 2) return "00:00:00"
        
        val hour = try { timeParts[0].toInt() } catch (e: Exception) { 0 }
        val min = try { timeParts[1].toInt() } catch (e: Exception) { 0 }
        
        val prayerCal = Calendar.getInstance().apply {
            var h = hour
            if (isPm && hour != 12) h += 12
            else if (isAm && hour == 12) h = 0
            
            set(Calendar.HOUR_OF_DAY, h)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
        }
        
        if (prayerCal.before(now)) prayerCal.add(Calendar.DAY_OF_MONTH, 1)
        
        val diff = prayerCal.timeInMillis - now.timeInMillis
        val h = diff / 3600000
        val m = (diff % 3600000) / 60000
        val s = (diff % 60000) / 1000
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", h, m, s)
    }

    public override fun handleIntent(intent: PrayerTimeIntent) {
        when (intent) {
            is PrayerTimeIntent.LoadPrayerTimes -> loadPrayerTimes()
            is PrayerTimeIntent.ChangeLocation -> {
                dataStoreRepository.latitude = intent.lat
                dataStoreRepository.longitude = intent.lng
                dataStoreRepository.locationName = intent.locationName
                onAction(PrayerTimeAction.OnLocationChanged(intent.lat, intent.lng, intent.locationName))
                loadPrayerTimes(intent.lat, intent.lng, intent.locationName)
            }
            is PrayerTimeIntent.ToggleNotification -> {
                onAction(PrayerTimeAction.OnNotificationToggled(intent.prayerType))
            }
            is PrayerTimeIntent.OpenQibla -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.QiblaScreen))
            }
            is PrayerTimeIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
            is PrayerTimeIntent.DismissError -> {
                onAction(PrayerTimeAction.ClearError)
            }
            is PrayerTimeIntent.ChangeCalculationMethod -> {
                onAction(PrayerTimeAction.OnMethodChanged(intent.methodId))
                loadPrayerTimes() // Reload with new method
            }
            is PrayerTimeIntent.ChangeSchool -> {
                onAction(PrayerTimeAction.OnSchoolChanged(intent.schoolId))
                loadPrayerTimes() // Reload with new school
            }
            is PrayerTimeIntent.ChangeTimeFormat -> {
                dataStoreRepository.is24HourFormat = intent.is24Hour
                onAction(PrayerTimeAction.OnTimeFormatChanged(intent.is24Hour))
                loadPrayerTimes()
            }
            is PrayerTimeIntent.LoadMonthlyCalendar -> loadMonthlyCalendar()
        }
    }

    private fun loadMonthlyCalendar() {
        val lat = viewState.latitude
        val lng = viewState.longitude
        viewModelScope.launch {
            onAction(PrayerTimeAction.OnLoading(isLoading = true))
            try {
                val now = Calendar.getInstance()
                val response = prayerTimeService.getMonthlyCalendarByLocation(
                    year = now.get(Calendar.YEAR),
                    month = now.get(Calendar.MONTH) + 1,
                    latitude = lat,
                    longitude = lng,
                    method = viewState.selectedMethodId,
                    school = viewState.selectedSchoolId,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
                if (response.code == 200) {
                    onAction(PrayerTimeAction.OnMonthlyCalendarLoaded(response.data))
                } else {
                    onAction(PrayerTimeAction.OnError("Error: ${response.code}"))
                }
            } catch (e: Exception) {
                onAction(PrayerTimeAction.OnError(e.message ?: "Unknown error"))
            } finally {
                onAction(PrayerTimeAction.OnLoading(isLoading = false))
            }
        }
    }

    private fun loadPrayerTimes(
        lat: Double = viewState.latitude,
        lng: Double = viewState.longitude,
        locationName: String = viewState.locationName
    ) {
        val method = viewState.selectedMethodId
        val school = viewState.selectedSchoolId

        getCombinedPrayerInfoUseCase(lat, lng, method, school)
            .onEach { result ->
                when (result) {
                    is PrayerTimesResult.Loading -> {
                        onAction(PrayerTimeAction.OnLoading(isLoading = true))
                    }
                    is PrayerTimesResult.Success -> {
                        val combinedInfo: CombinedPrayerInfo = result.data
                        val allPrayers = combinedInfo.allPrayers
                        val nextPrayerInfo = combinedInfo.nextPrayer

                        val prayerDetailsList = allPrayers.map { prayer: IPrayerRepository.PrayerWithStatus ->
                            PrayerDetails(
                                name = when (prayer.name.lowercase()) {
                                    "fajr" -> "الفجر"
                                    "sunrise" -> "الشروق"
                                    "dhuhr" -> "الظهر"
                                    "asr" -> "العصر"
                                    "maghrib" -> "المغرب"
                                    "isha" -> "العشاء"
                                    "imsak" -> "الإمساك"
                                    "midnight" -> "منتصف الليل"
                                    "firstthird" -> "الثلث الأول"
                                    "lastthird" -> "الثلث الأخير"
                                    else -> prayer.name
                                },
                                time = TimeFormatUtils.formatTime(prayer.time, viewState.is24HourFormat),
                                isNext = prayer.name == nextPrayerInfo.prayerName,
                                isPassed = prayer.status == PrayerStatus.COMPLETED,
                                remainingTime = if (prayer.name == nextPrayerInfo.prayerName) nextPrayerInfo.remainingTime else "00:00:00",
                                type = when (prayer.name.lowercase()) {
                                    "fajr" -> PrayerType.FAJR
                                    "sunrise" -> PrayerType.SUNRISE
                                    "dhuhr" -> PrayerType.DHUHR
                                    "asr" -> PrayerType.ASR
                                    "maghrib" -> PrayerType.MAGHRIB
                                    "isha" -> PrayerType.ISHA
                                    "imsak" -> PrayerType.IMSAK
                                    "midnight" -> PrayerType.MIDNIGHT
                                    "firstthird" -> PrayerType.FIRST_THIRD
                                    "lastthird" -> PrayerType.LAST_THIRD
                                    else -> PrayerType.FAJR
                                }
                            )
                        }

                        onAction(
                            PrayerTimeAction.OnPrayerTimesLoaded(
                                location = locationName,
                                hijriDate = nextPrayerInfo.hijriDate,
                                gregorianDate = nextPrayerInfo.gregorianDate,
                                prayerTimes = prayerDetailsList
                            )
                        )
                    }
                    is PrayerTimesResult.Error -> {
                        onAction(PrayerTimeAction.OnError(result.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
