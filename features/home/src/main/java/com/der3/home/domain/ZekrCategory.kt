package com.der3.home.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

data class ZekrCategory(
    val title: String,
    val count: String,
    val icon: ImageVector
)


    /*
        ZekrCategory("أذكار الصباح", "24 ذكراً", Icons.Default.WbSunny),
        ZekrCategory("أذكار المساء", "18 ذكراً", Icons.Default.NightsStay),
        ZekrCategory("أذكار الصلاة", "12 ذكراً", Icons.Default.Mosque),
        ZekrCategory("أذكار النوم", "15 ذكراً", Icons.Default.Bedtime),
        ZekrCategory("أذكار السفر", "8 أذكار", Icons.Default.FlightTakeoff),
        ZekrCategory("أذكار الاستيقاظ", "10 أذكار", Icons.Default.LightMode),
        ZekrCategory("الذكر بعد الفراغ من الوضوء", "1 ذكر", Icons.Default.WaterDrop),
        ZekrCategory("الذكر عند الخروج من المنزل", "1 ذكر", Icons.AutoMirrored.Filled.DirectionsWalk)
    */

val zekrCategories = listOf(

    // 🌤 يومي
    ZekrCategory("أذكار الصباح والمساء", "42 ذكراً", Icons.Default.WbSunny),
    ZekrCategory("أذكار النوم", "15 ذكراً", Icons.Default.Bedtime),
    ZekrCategory("أذكار الاستيقاظ من النوم", "10 أذكار", Icons.Default.LightMode),

    // 🚪 المنزل والطهارة
    ZekrCategory("دعاء دخول الخلاء", "1 ذكر", Icons.Filled.DoorFront),
    ZekrCategory("أذكار الآذان", "5 أذكار", Icons.Default.Mosque),

    ZekrCategory("الذكر قبل الوضوء", "1 ذكر", Icons.Default.WaterDrop),
    ZekrCategory("الذكر بعد الفراغ من الوضوء", "3 أذكار", Icons.Default.CheckCircle),
    ZekrCategory("الذكر عند الخروج من المنزل", "2 ذكر", Icons.AutoMirrored.Filled.DirectionsWalk),
    ZekrCategory("الذكر عند دخول المنزل", "2 ذكر", Icons.Default.Home),
    ZekrCategory("دعاء الخروج من الخلاء", "1 ذكر", Icons.AutoMirrored.Filled.Logout),

    // 🕌 المسجد والصلاة
    ZekrCategory("دعاء الذهاب إلى المسجد", "2 ذكر", Icons.AutoMirrored.Filled.DirectionsWalk),
    ZekrCategory("دعاء دخول المسجد", "2 ذكر", Icons.Default.Mosque),
    ZekrCategory("دعاء الخروج من المسجد", "1 ذكر", Icons.Default.Mosque),
    ZekrCategory("أذكار الآذان", "5 أذكار", Icons.Default.Campaign),
    ZekrCategory("دعاء الاستفتاح", "6 صيغ", Icons.AutoMirrored.Filled.MenuBook),
    ZekrCategory("دعاء الركوع", "3 صيغ", Icons.Default.ArrowDownward),
    ZekrCategory("دعاء الرفع من الركوع", "2 ذكر", Icons.Default.ArrowUpward),
    ZekrCategory("دعاء السجود", "5 صيغ", Icons.Default.South),
    ZekrCategory("دعاء الجلسة بين السجدتين", "2 ذكر", Icons.Default.PanTool),
    ZekrCategory("دعاء سجود التلاوة", "1 ذكر", Icons.Default.ImportContacts),
    ZekrCategory("التشهد", "2 صيغة", Icons.Default.Notes),
    ZekrCategory("الصلاة على النبي بعد التشهد", "3 صيغ", Icons.Default.Favorite),
    ZekrCategory("الدعاء بعد التشهد الأخير قبل السلام", "6 أدعية", Icons.Default.VolunteerActivism),
    ZekrCategory("الأذكار بعد السلام من الصلاة", "8 أذكار", Icons.Default.CheckCircle),
    ZekrCategory("دعاء صلاة الاستخارة", "1 ذكر", Icons.AutoMirrored.Filled.Help),
    ZekrCategory("دعاء قنوت الوتر", "1 ذكر", Icons.Default.NightsStay),
    ZekrCategory("الذكر عقب السلام من الوتر", "3 أذكار", Icons.Default.CheckCircle),

    // 👕 اللباس والطعام
    ZekrCategory("دعاء لبس الثوب", "1 ذكر", Icons.Default.Checkroom),
    ZekrCategory("دعاء لبس الثوب الجديد", "1 ذكر", Icons.Default.Star),
    ZekrCategory("الدعاء لمن لبس ثوبا جديدا", "1 ذكر", Icons.Default.FavoriteBorder),
    ZekrCategory("ما يقول إذا وضع ثوبه", "1 ذكر", Icons.Default.Inventory2),
    ZekrCategory("الدعاء قبل الطعام", "1 ذكر", Icons.Default.Restaurant),
    ZekrCategory("الدعاء عند الفراغ من الطعام", "1 ذكر", Icons.Default.Restaurant),
    ZekrCategory("دعاء الضيف لصاحب الطعام", "1 ذكر", Icons.Default.Groups),

    // ✈️ السفر
    ZekrCategory("دعاء الركوب", "1 ذكر", Icons.Default.DirectionsCar),
    ZekrCategory("دعاء السفر", "3 أذكار", Icons.Default.FlightTakeoff),
    ZekrCategory("ذكر الرجوع من السفر", "2 ذكر", Icons.Default.FlightLand),
    ZekrCategory("دعاء دخول القرية أو البلدة", "1 ذكر", Icons.Default.LocationCity),
    ZekrCategory("دعاء دخول السوق", "2 ذكر", Icons.Default.Store),

    // 🌧 الطبيعة
    ZekrCategory("دعاء الريح", "1 ذكر", Icons.Default.Air),
    ZekrCategory("دعاء الرعد", "1 ذكر", Icons.Default.Thunderstorm),
    ZekrCategory("الدعاء إذا نزل المطر", "2 ذكر", Icons.Default.Umbrella),
    ZekrCategory("دعاء رؤية الهلال", "1 ذكر", Icons.Default.Brightness2),

    // 🤲 الحزن والكرب
    ZekrCategory("دعاء الهم والحزن", "3 أدعية", Icons.Default.SentimentDissatisfied),
    ZekrCategory("دعاء الكرب", "3 أدعية", Icons.Default.Warning),
    ZekrCategory("دعاء قضاء الدين", "2 ذكر", Icons.Default.Payments),
    ZekrCategory("الاستغفار و التوبة", "5 صيغ", Icons.Default.Refresh),

    // ❤️ العلاقات
    ZekrCategory("الدعاء للمتزوج", "1 ذكر", Icons.Default.Favorite),
    ZekrCategory("الدعاء قبل إتيان الزوجة", "1 ذكر", Icons.Default.FavoriteBorder),
    ZekrCategory("دعاء الغضب", "1 ذكر", Icons.Default.LocalFireDepartment),

    // 🏥 المرض والموت
    ZekrCategory("الدعاء للمريض في عيادته", "3 أدعية", Icons.Default.MedicalServices),
    ZekrCategory("دعاء من أصيب بمصيبة", "2 ذكر", Icons.Default.Healing),
    ZekrCategory("دعاء زيارة القبور", "2 ذكر", Icons.Default.Landscape),

    // 🕋 الحج
    ZekrCategory("كيف يلبي المحرم في الحج أو العمرة ؟", "1 ذكر", Icons.Default.Mosque),
    ZekrCategory("التكبير إذا أتى الركن الأسود", "1 ذكر", Icons.Default.Star),
    ZekrCategory("الدعاء يوم عرفة", "3 أدعية", Icons.Default.WbSunny),

    // 🌟 عامة
    ZekrCategory("إفشاء السلام", "1 ذكر", Icons.Default.Handshake),
    ZekrCategory("فضل الصلاة على النبي صلى الله عليه وسلم", "5 صيغ", Icons.Default.Favorite),
    ZekrCategory("كيف كان النبي يسبح؟", "3 صيغ", Icons.Default.AutoAwesome),
    ZekrCategory("من أنواع الخير والآداب الجامعة", "10 أبواب", Icons.Default.VolunteerActivism)

)
