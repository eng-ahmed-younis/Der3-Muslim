package com.der3.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import com.der3.ui.models.CategoryUi

object CategoryMock {
    val mockSections = listOf(
        CategoryUi(
            id = 6,
            title = "أذكار",
            subtitle = "حصن المسلم والأدعية",
            icon = Icons.AutoMirrored.Filled.MenuBook
        ),
        CategoryUi(
            id = 1,
            title = "القبلة",
            subtitle = "تحديد اتجاه الكعبة",
            icon = Icons.Default.Explore
        ),
        CategoryUi(
            id = 7,
            title = "القرآن الكريم",
            subtitle = "قراءة واستماع السور",
            icon = Icons.Default.MenuBook
        ),
        CategoryUi(
            id = 2,
            title = "المسبحة",
            subtitle = "التسبيح الإلكتروني",
            icon = Icons.Default.Pin
        ),
        CategoryUi(
            id = 8,
            title = "التنبيهات",
            subtitle = "مواعيد الصلاة والأذكار",
            icon = Icons.Default.Notifications
        ),
        CategoryUi(
            id = 3,
            title = "المفضلة",
            subtitle = "محفوظاتك الخاصة",
            icon = Icons.Default.Bookmark
        ),
        CategoryUi(
            id = 9,
            title = "أوقات الصلاة",
            subtitle = "مواقيت دقيقة حسب موقعك",
            icon = Icons.Default.Schedule
        ),
        CategoryUi(
            id = 4,
            title = "التقويم الهجري",
            subtitle = "المناسبات والشهور الهجرية",
            icon = Icons.Default.CalendarMonth
        ),
        CategoryUi(
            id = 10,
            title = "رقية شرعية",
            subtitle = "الرقية من الكتاب والسنة",
            icon = Icons.Default.Security
        ),
        CategoryUi(
            id = 5,
            title = "أسماء الله الحسنى",
            subtitle = "شرح ومعاني أسماء الله",
            icon = Icons.Default.Star
        ),
        CategoryUi(
            id = 11,
            title = "خاتمة القرآن",
            subtitle = "متابعة ورد الختمة",
            icon = Icons.AutoMirrored.Filled.MenuBook
        ),
        CategoryUi(
            id = 12,
            title = "السيرة النبوية",
            subtitle = "قصة حياة النبي ﷺ",
            icon = Icons.Default.Person
        ),
        CategoryUi(
            id = 13,
            title = "الحديث الشريف",
            subtitle = "أحاديث صحيحة من السنة النبوية",
            icon = Icons.Default.HistoryEdu
        ),
        CategoryUi(
            id = 14,
            title = "الزكاة والصدقة",
            subtitle = "حاسبة الزكاة وأنواع الصدقات",
            icon = Icons.Default.Calculate
        ),
        CategoryUi(
            id = 15,
            title = "الصيام",
            subtitle = "متابعة أيام الصيام والنوافل",
            icon = Icons.Default.WbSunny
        ),
        CategoryUi(
            id = 16,
            title = "قصص الأنبياء",
            subtitle = "سير الأنبياء والمرسلين",
            icon = Icons.Default.Favorite
        ),
    )
}
