package com.der3.sections.presentation.prayer.prayer_times.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.der3.sections.domain.model.CalculationMethodUi
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeIntent
import com.der3.sections.presentation.prayer.prayer_times.mvi.PrayerTimeState
import com.der3.sections.presentation.utils.prayer.CalculationMethodsList
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto
import com.der3.ui.themes.AppColors
import com.der3.utils.TimeFormatUtils
import com.der3.ui.R
import java.util.Calendar

@Composable
fun CalculationMethodDialog(
    state: PrayerTimeState,
    onIntent: (PrayerTimeIntent) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = stringResource(R.string.prayer_times_settings),
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppColors.green900,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        SectionHeader(title = stringResource(R.string.calculation_method))
                    }

                    items(CalculationMethodsList.items) { method ->
                        MethodItem(
                            method = method,
                            isSelected = method.id == state.selectedMethodId,
                            onClick = { onIntent(PrayerTimeIntent.ChangeCalculationMethod(method.id)) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader(title = stringResource(R.string.juridical_school))
                    }

                    item {
                        SchoolItem(
                            title = stringResource(R.string.shafii_hanbali_maliki),
                            isSelected = state.selectedSchoolId == 0,
                            onClick = { onIntent(PrayerTimeIntent.ChangeSchool(0)) }
                        )
                    }

                    item {
                        SchoolItem(
                            title = stringResource(R.string.hanafi),
                            isSelected = state.selectedSchoolId == 1,
                            onClick = { onIntent(PrayerTimeIntent.ChangeSchool(1)) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader(title = stringResource(R.string.time_format))
                    }

                    item {
                        TimeFormatItem(
                            title = stringResource(R.string.time_format_12h),
                            isSelected = !state.is24HourFormat,
                            onClick = { onIntent(PrayerTimeIntent.ChangeTimeFormat(false)) }
                        )
                    }

                    item {
                        TimeFormatItem(
                            title = stringResource(R.string.time_format_24h),
                            isSelected = state.is24HourFormat,
                            onClick = { onIntent(PrayerTimeIntent.ChangeTimeFormat(true)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.green800)
                ) {
                    Text(stringResource(R.string.save), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun LocationSelectionDialog(
    state: PrayerTimeState,
    onIntent: (PrayerTimeIntent) -> Unit,
    onDismiss: () -> Unit
) {
    val cities = listOf(
        Triple(24.7136, 46.6753, "الرياض، السعودية"),
        Triple(21.3891, 39.8579, "مكة المكرمة، السعودية"),
        Triple(24.4672, 39.6024, "المدينة المنورة، السعودية"),
        Triple(30.0444, 31.2357, "القاهرة، مصر"),
        Triple(25.2048, 55.2708, "دبي، الإمارات"),
        Triple(33.3406, 44.4009, "بغداد، العراق"),
        Triple(31.9454, 35.9284, "عمان، الأردن"),
        Triple(33.5138, 36.2765, "دمشق، سوريا"),
        Triple(33.8938, 35.5018, "بيروت، لبنان"),
        Triple(29.3759, 47.9774, "الكويت"),
        Triple(25.2854, 51.5310, "الدوحة، قطر"),
        Triple(26.2285, 50.5860, "المنامة، البحرين"),
        Triple(23.5859, 58.4059, "مسقط، عمان")
    )

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val index = cities.indexOfFirst { it.third == state.locationName }
        if (index >= 0) {
            listState.scrollToItem(index)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = stringResource(R.string.location_settings),
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppColors.green900,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cities) { (lat, lng, name) ->
                        CityItem(
                            name = name,
                            isSelected = name == state.locationName,
                            onClick = {
                                onIntent(PrayerTimeIntent.ChangeLocation(lat, lng, name))
                                onDismiss()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(R.string.close), color = AppColors.gray500)
                }
            }
        }
    }
}

@Composable
fun MonthlyCalendarDialog(
    state: PrayerTimeState,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(vertical = 20.dp)) {
                val monthNameEn = state.monthlyCalendar.firstOrNull()?.date?.gregorian?.month?.en ?: ""
                val monthNameAr = when (monthNameEn.lowercase()) {
                    "january" -> "يناير"
                    "february" -> "فبراير"
                    "march" -> "مارس"
                    "april" -> "أبريل"
                    "may" -> "مايو"
                    "june" -> "يونيو"
                    "july" -> "يوليو"
                    "august" -> "أغسطس"
                    "september" -> "سبتمبر"
                    "october" -> "أكتوبر"
                    "november" -> "نوفمبر"
                    "december" -> "ديسمبر"
                    else -> monthNameEn
                }

                Text(
                    text = "تقويم شهر $monthNameAr",
                    style = MaterialTheme.typography.titleLarge,
                    color = AppColors.green900,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .background(AppColors.green800, RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderText(stringResource(R.string.day), Modifier.weight(1.5f), color = Color.White)
                    HeaderText(stringResource(R.string.fajr), Modifier.weight(1f), color = Color.White)
                    HeaderText(stringResource(R.string.dhuhr), Modifier.weight(1f), color = Color.White)
                    HeaderText(stringResource(R.string.asr), Modifier.weight(1f), color = Color.White)
                    HeaderText(stringResource(R.string.maghrib), Modifier.weight(1f), color = Color.White)
                    HeaderText(stringResource(R.string.isha), Modifier.weight(1f), color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                val listState = rememberLazyListState()
                
                LaunchedEffect(state.monthlyCalendar) {
                    val index = state.monthlyCalendar.indexOfFirst { 
                        try { it.date.gregorian.day.toInt() == today } catch (e: Exception) { false }
                    }
                    if (index >= 0) {
                        listState.animateScrollToItem(index)
                    }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(state.monthlyCalendar) { day ->
                        val isToday = try { 
                            day.date.gregorian.day.toInt() == today 
                        } catch (e: Exception) { 
                            false 
                        }
                        CalendarRow(day, isToday, state.is24HourFormat)
                    }
                }

                HorizontalDivider(color = AppColors.gray200)

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.close), color = AppColors.green800, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = AppColors.green800,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun MethodItem(
    method: CalculationMethodUi,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) AppColors.green50 else AppColors.gray50,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) AppColors.green800 else AppColors.gray300),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(method.nameResId),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) AppColors.green900 else AppColors.gray900Text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun SchoolItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) AppColors.green50 else AppColors.gray50,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = AppColors.green800)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) AppColors.green900 else AppColors.gray900Text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun TimeFormatItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) AppColors.green50 else AppColors.gray50,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = AppColors.green800)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) AppColors.green900 else AppColors.gray900Text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun CityItem(
    name: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) AppColors.green50 else AppColors.gray50,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (isSelected) AppColors.green800 else AppColors.green700,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) AppColors.green900 else AppColors.gray900Text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )

            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = AppColors.green800,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun HeaderText(text: String, modifier: Modifier, color: Color = AppColors.green900) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 13.sp,
        color = color
    )
}

@Composable
private fun CalendarRow(day: PrayerTimesDto, isToday: Boolean, is24Hour: Boolean = false) {
    val isFriday = day.date.gregorian.weekday.en == "Friday"
    
    val backgroundColor = when {
        isToday -> AppColors.green800
        isFriday -> AppColors.green50.copy(alpha = 0.5f)
        else -> Color.Transparent
    }
    
    val contentColor = if (isToday) Color.White else AppColors.gray900Text
    val secondaryColor = if (isToday) Color.White.copy(alpha = 0.7f) else AppColors.gray500
    val amPmColor = if (isToday) Color.White else AppColors.green700
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        tonalElevation = if (isToday) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.date.gregorian.day,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isToday) Color.White else (if (isFriday) AppColors.green800 else AppColors.gray900Text)
                )
                Text(
                    text = day.date.hijri.day + " " + day.date.hijri.month.ar,
                    fontSize = 9.sp,
                    color = secondaryColor,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )
            }
            TimeText(day.timings.fajr, Modifier.weight(1f), is24Hour, contentColor, amPmColor)
            TimeText(day.timings.dhuhr, Modifier.weight(1f), is24Hour, contentColor, amPmColor)
            TimeText(day.timings.asr, Modifier.weight(1f), is24Hour, contentColor, amPmColor)
            TimeText(day.timings.maghrib, Modifier.weight(1f), is24Hour, contentColor, amPmColor)
            TimeText(day.timings.isha, Modifier.weight(1f), is24Hour, contentColor, amPmColor)
        }
    }
    if (!isToday) {
        HorizontalDivider(color = AppColors.gray50, thickness = 1.dp)
    }
}

@Composable
private fun TimeText(
    time: String, 
    modifier: Modifier, 
    is24Hour: Boolean = false,
    contentColor: Color = AppColors.gray900Text,
    amPmColor: Color = AppColors.green700
) {
    val formattedTime = TimeFormatUtils.formatTime(time, is24Hour)
    val timeParts = formattedTime.split(" ")
    val timeValue = timeParts[0]
    val amPm = if (timeParts.size > 1) timeParts[1] else ""

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeValue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor,
            textAlign = TextAlign.Center
        )
        if (amPm.isNotEmpty()) {
            Text(
                text = amPm,
                fontSize = 8.sp,
                color = amPmColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
