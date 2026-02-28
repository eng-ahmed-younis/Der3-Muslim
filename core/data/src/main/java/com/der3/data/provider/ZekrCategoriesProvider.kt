package com.der3.data.provider

import androidx.compose.material.icons.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.automirrored.filled.SpeakerNotes
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.ChangeHistory
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.GppBad
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.NoMeals
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SpeakerNotes
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.*
import com.der3.data.model.AzkarCategory
import com.der3.ui.models.CategoryUi

object ZekrCategoriesProvider {

    val categories = listOf(

        // 🌤 يومي
        CategoryUi(id = 1, title = "أذكار الصباح والمساء", subtitle = "أوراد اليوم والليلة الواجبة", count = "24 ذكراً", icon = Icons.Default.WbSunny),
        CategoryUi(id = 2, title = "أذكار النوم", subtitle = "ما يقال عند النوم والاستعاذة", count = "13 ذكراً", icon = Icons.Default.Bedtime),
        CategoryUi(id = 3, title = "أذكار الاستيقاظ من النوم", subtitle = "أذكار الصباح الباكر عند الاستيقاظ", count = "4 أذكار", icon = Icons.Default.LightMode),

        // 🚪 الخلاء والطهارة
        CategoryUi(id = 4, title = "دعاء دخول الخلاء", subtitle = "الاستعاذة عند دخول الحمام", count = "1 ذكر", icon = Icons.Default.DoorFront),
        CategoryUi(id = 5, title = "دعاء الخروج من الخلاء", subtitle = "ما يقال عند الخروج من الحمام", count = "1 ذكر", icon = Icons.AutoMirrored.Filled.Logout),
        CategoryUi(id = 6, title = "الذكر قبل الوضوء", subtitle = "البسملة قبل الوضوء", count = "1 ذكر", icon = Icons.Default.WaterDrop),
        CategoryUi(id = 7, title = "الذكر بعد الفراغ من الوضوء", subtitle = "الشهادة والدعاء بعد الوضوء", count = "3 أذكار", icon = Icons.Default.CheckCircle),

        // 🏠 المنزل
        CategoryUi(id = 8, title = "الذكر عند الخروج من المنزل", subtitle = "التوكل على الله عند الخروج", count = "2 ذكر", icon = Icons.AutoMirrored.Filled.DirectionsWalk),
        CategoryUi(id = 9, title = "الذكر عند دخول المنزل", subtitle = "البسملة والسلام عند الدخول", count = "1 ذكر", icon = Icons.Default.Home),

        // 🕌 المسجد
        CategoryUi(id = 10, title = "دعاء الذهاب إلى المسجد", subtitle = "طلب النور في الطريق إلى المسجد", count = "1 ذكر", icon = Icons.AutoMirrored.Filled.DirectionsWalk),
        CategoryUi(id = 11, title = "دعاء دخول المسجد", subtitle = "الاستعاذة وطلب أبواب الرحمة", count = "1 ذكر", icon = Icons.Default.Mosque),
        CategoryUi(id = 12, title = "دعاء الخروج من المسجد", subtitle = "طلب الفضل عند مغادرة المسجد", count = "1 ذكر", icon = Icons.Default.Mosque),

        // 📢 الأذان
        CategoryUi(id = 13, title = "أذكار الآذان", subtitle = "الإجابة والدعاء عقب الأذان", count = "5 أذكار", icon = Icons.Default.Campaign),

        // 👕 اللباس
        CategoryUi(id = 14, title = "دعاء لُبْس الثوب", subtitle = "حمد الله على نعمة اللباس", count = "1 ذكر", icon = Icons.Default.Checkroom),
        CategoryUi(id = 15, title = "دعاء لُبْس الثوب الجديد", subtitle = "الدعاء عند ارتداء ثوب جديد", count = "1 ذكر", icon = Icons.Default.Star),
        CategoryUi(id = 16, title = "الدعاء لمن لبس ثوباً جديداً", subtitle = "التهنئة بالثوب الجديد", count = "2 ذكر", icon = Icons.Default.FavoriteBorder),
        CategoryUi(id = 17, title = "ما يقول إذا وضع ثوبه", subtitle = "البسملة عند خلع الثوب", count = "1 ذكر", icon = Icons.Default.Inventory2),

        // 🙏 الصلاة
        CategoryUi(id = 18, title = "دعاء الاستفتاح", subtitle = "دعاء الافتتاح في أول الصلاة", count = "6 صيغ", icon = Icons.AutoMirrored.Filled.MenuBook),
        CategoryUi(id = 19, title = "دعاء الركوع", subtitle = "التسبيح والذكر في الركوع", count = "5 صيغ", icon = Icons.Default.ArrowDownward),
        CategoryUi(id = 20, title = "دعاء الرفع من الركوع", subtitle = "الحمد عند الرفع من الركوع", count = "3 صيغ", icon = Icons.Default.ArrowUpward),
        CategoryUi(id = 21, title = "دعاء السجود", subtitle = "التسبيح والدعاء في السجود", count = "7 صيغ", icon = Icons.Default.South),
        CategoryUi(id = 22, title = "دعاء الجلسة بين السجدتين", subtitle = "طلب المغفرة بين السجدتين", count = "2 ذكر", icon = Icons.Default.PanTool),
        CategoryUi(id = 23, title = "دعاء سجود التلاوة", subtitle = "الذكر عند سجود تلاوة القرآن", count = "2 ذكر", icon = Icons.Default.ImportContacts),
        CategoryUi(id = 24, title = "التشهد", subtitle = "التحيات والشهادتان في الصلاة", count = "1 صيغة", icon = Icons.Default.Notes),
        CategoryUi(id = 25, title = "الصلاة على النبي بعد التشهد", subtitle = "الصلاة الإبراهيمية على النبي ﷺ", count = "2 صيغة", icon = Icons.Default.Favorite),
        CategoryUi(id = 26, title = "الدعاء بعد التشهد الأخير قبل السلام", subtitle = "الاستعاذة والدعاء قبل السلام", count = "11 أدعية", icon = Icons.Default.VolunteerActivism),
        CategoryUi(id = 27, title = "الأذكار بعد السلام من الصلاة", subtitle = "الاستغفار والتسبيح بعد الصلاة", count = "8 أذكار", icon = Icons.Default.CheckCircle),
        CategoryUi(id = 28, title = "دعاء صلاة الاستخارة", subtitle = "الاستخارة في الأمور كلها", count = "1 ذكر", icon = Icons.AutoMirrored.Filled.Help),

        // 😴 النوم
        CategoryUi(id = 29, title = "الدعاء إذا تقلّب ليلاً", subtitle = "الذكر عند التقلب أثناء النوم", count = "1 ذكر", icon = Icons.Default.NightsStay),
        CategoryUi(id = 30, title = "دعاء الفزع في النوم ومن بُلي بالوحشة", subtitle = "الاستعاذة من أهوال المنام", count = "1 ذكر", icon = Icons.Default.Warning),
        CategoryUi(id = 31, title = "ما يفعل من رأى الرؤيا أو الحلم", subtitle = "آداب الرؤيا الحسنة والمكروهة", count = "2 ذكر", icon = Icons.Default.Visibility),

        // 🌙 الوتر
        CategoryUi(id = 32, title = "دعاء قنوت الوتر", subtitle = "الدعاء في قنوت صلاة الوتر", count = "3 صيغ", icon = Icons.Default.NightsStay),
        CategoryUi(id = 33, title = "الذكر عقب السلام من الوتر", subtitle = "تسبيح الملك القدوس بعد الوتر", count = "1 ذكر", icon = Icons.Default.CheckCircle),

        // 😔 الهم والكرب
        CategoryUi(id = 34, title = "دعاء الهم والحزن", subtitle = "أدعية الفرج من الهم والضيق", count = "2 دعاء", icon = Icons.Default.SentimentDissatisfied),
        CategoryUi(id = 35, title = "دعاء الكرب", subtitle = "الذكر والدعاء عند شدة الكرب", count = "4 أدعية", icon = Icons.Default.Warning),

        // ⚔️ العدو والسلطان
        CategoryUi(id = 36, title = "دعاء لقاء العدو وذي السلطان", subtitle = "الاستعانة بالله عند مواجهة العدو", count = "3 أدعية", icon = Icons.Default.Shield),
        CategoryUi(id = 37, title = "دعاء من خاف ظلم السلطان", subtitle = "اللجوء إلى الله من جور السلطان", count = "2 دعاء", icon = Icons.Default.GppMaybe),
        CategoryUi(id = 38, title = "الدعاء على العدو", subtitle = "دعاء هزيمة الأعداء", count = "1 ذكر", icon = Icons.Default.SportsKabaddi),
        CategoryUi(id = 39, title = "ما يقول من خاف قوماً", subtitle = "الاستعانة بالله من شر الأعداء", count = "1 ذكر", icon = Icons.Default.PersonOff),

        // 🧠 الوساوس
        CategoryUi(id = 40, title = "دعاء من أصابه وسوسة في الإيمان", subtitle = "علاج الوسواس في أمور العقيدة", count = "3 أذكار", icon = Icons.Default.Psychology),
        CategoryUi(id = 41, title = "دعاء قضاء الدين", subtitle = "الدعاء للفرج من الديون", count = "2 دعاء", icon = Icons.Default.Payments),
        CategoryUi(id = 42, title = "دعاء الوسوسة في الصلاة والقراءة", subtitle = "رد وسواس الشيطان في الصلاة", count = "1 ذكر", icon = Icons.Default.Block),
        CategoryUi(id = 43, title = "دعاء من استصعب عليه أمر", subtitle = "طلب التيسير عند المشقة", count = "1 ذكر", icon = Icons.Default.Construction),
        CategoryUi(id = 44, title = "ما يقول ويفعل من أذنب ذنباً", subtitle = "التوبة والاستغفار من الذنوب", count = "1 ذكر", icon = Icons.Default.Refresh),
        CategoryUi(id = 45, title = "دعاء طرد الشيطان ووساوسه", subtitle = "الاستعاذة والتحصن من الشيطان", count = "3 أذكار", icon = Icons.Default.RemoveCircle),
        CategoryUi(id = 46, title = "الدعاء حينما يقع ما لا يرضاه أو غُلب على أمره", subtitle = "الرضا بالقدر والتسليم لله", count = "1 ذكر", icon = Icons.Default.SentimentNeutral),

        // 👶 المولود والأولاد
        CategoryUi(id = 47, title = "تهنئة المولود له وجوابه", subtitle = "الدعاء للمولود الجديد وأهله", count = "1 ذكر", icon = Icons.Default.ChildCare),
        CategoryUi(id = 48, title = "ما يعوّذ به الأولاد", subtitle = "تعويذ الأطفال من الشيطان والعين", count = "1 ذكر", icon = Icons.Default.FamilyRestroom),

        // 🏥 المرض
        CategoryUi(id = 49, title = "الدعاء للمريض في عيادته", subtitle = "الدعاء بالشفاء عند زيارة المريض", count = "2 دعاء", icon = Icons.Default.MedicalServices),
        CategoryUi(id = 50, title = "فضل عيادة المريض", subtitle = "ثواب زيارة المريض المسلم", count = "1 ذكر", icon = Icons.Default.Healing),
        CategoryUi(id = 51, title = "دعاء المريض الذي يئس من حياته", subtitle = "ما يقوله المريض عند الاحتضار", count = "3 أدعية", icon = Icons.Default.Favorite),

        // 💀 الموت والجنازة
        CategoryUi(id = 52, title = "تلقين المحتضر", subtitle = "لا إله إلا الله آخر الكلام", count = "1 ذكر", icon = Icons.Default.AccessibilityNew),
        CategoryUi(id = 53, title = "دعاء من أصيب بمصيبة", subtitle = "الاسترجاع عند نزول المصيبة", count = "1 ذكر", icon = Icons.Default.SentimentDissatisfied),
        CategoryUi(id = 54, title = "الدعاء عند إغماض الميت", subtitle = "الدعاء للميت عند تغميض عينيه", count = "1 ذكر", icon = Icons.Default.NightsStay),
        CategoryUi(id = 55, title = "الدعاء للميت في الصلاة عليه", subtitle = "أدعية صلاة الجنازة للميت", count = "4 أدعية", icon = Icons.Default.VolunteerActivism),
        CategoryUi(id = 56, title = "الدعاء للفرط في الصلاة عليه", subtitle = "الدعاء لصلاة جنازة الطفل", count = "2 دعاء", icon = Icons.Default.ChildFriendly),
        CategoryUi(id = 57, title = "دعاء التعزية", subtitle = "كلمات العزاء في أهل الميت", count = "1 ذكر", icon = Icons.Default.Handshake),
        CategoryUi(id = 58, title = "الدعاء عند إدخال الميت القبر", subtitle = "ما يقال عند وضع الميت في القبر", count = "1 ذكر", icon = Icons.Default.Landscape),
        CategoryUi(id = 59, title = "الدعاء بعد دفن الميت", subtitle = "الدعاء للميت بعد إتمام الدفن", count = "1 ذكر", icon = Icons.Default.Landscape),
        CategoryUi(id = 60, title = "دعاء زيارة القبور", subtitle = "السلام على أهل القبور والدعاء لهم", count = "1 ذكر", icon = Icons.Default.Place),

        // 🌧 الطبيعة
        CategoryUi(id = 61, title = "دعاء الريح", subtitle = "طلب الخير والاستعاذة من الريح", count = "2 دعاء", icon = Icons.Default.Air),
        CategoryUi(id = 62, title = "دعاء الرعد", subtitle = "تسبيح الله عند سماع الرعد", count = "1 ذكر", icon = Icons.Default.Thunderstorm),
        CategoryUi(id = 63, title = "من أدعية الاستسقاء", subtitle = "طلب الغيث من الله تعالى", count = "3 أدعية", icon = Icons.Default.WaterDrop),
        CategoryUi(id = 64, title = "الدعاء إذا نزل المطر", subtitle = "الدعاء حين نزول المطر", count = "1 ذكر", icon = Icons.Default.Umbrella),
        CategoryUi(id = 65, title = "الذكر بعد نزول المطر", subtitle = "حمد الله بعد نزول الغيث", count = "1 ذكر", icon = Icons.Default.Grain),
        CategoryUi(id = 66, title = "من أدعية الاستصحاء", subtitle = "الدعاء بصرف المطر الضار", count = "1 ذكر", icon = Icons.Default.WbSunny),
        CategoryUi(id = 67, title = "دعاء رؤية الهلال", subtitle = "الدعاء عند رؤية الهلال الجديد", count = "1 ذكر", icon = Icons.Default.Brightness2),

        // 🍽 الطعام والصيام
        CategoryUi(id = 68, title = "الدعاء عند إفطار الصائم", subtitle = "ما يقوله الصائم عند الإفطار", count = "2 دعاء", icon = Icons.Default.Fastfood),
        CategoryUi(id = 69, title = "الدعاء قبل الطعام", subtitle = "البسملة والدعاء قبل الأكل", count = "2 دعاء", icon = Icons.Default.Restaurant),
        CategoryUi(id = 70, title = "الدعاء عند الفراغ من الطعام", subtitle = "حمد الله بعد الانتهاء من الطعام", count = "2 دعاء", icon = Icons.Default.Restaurant),
        CategoryUi(id = 71, title = "دعاء الضيف لصاحب الطعام", subtitle = "الدعاء لصاحب البيت بعد الطعام", count = "1 ذكر", icon = Icons.Default.Groups),
        CategoryUi(id = 72, title = "التعريض بالدعاء لطلب الطعام أو الشراب", subtitle = "الدعاء لمن أطعمك وسقاك", count = "1 ذكر", icon = Icons.Default.LocalDining),
        CategoryUi(id = 73, title = "الدعاء إذا أفطر عند أهل بيت", subtitle = "دعاء الإفطار عند المضيف", count = "1 ذكر", icon = Icons.Default.DinnerDining),
        CategoryUi(id = 74, title = "دعاء الصائم إذا حضر الطعام ولم يفطر", subtitle = "ما يفعله الصائم حين الدعوة للطعام", count = "1 ذكر", icon = Icons.Default.NoMeals),
        CategoryUi(id = 75, title = "ما يقول الصائم إذا سابّه أحد", subtitle = "رد الصائم على من يؤذيه", count = "1 ذكر", icon = Icons.Default.SpeakerNotes),
        CategoryUi(id = 76, title = "الدعاء عند رؤية باكورة الثمر", subtitle = "الدعاء بالبركة عند رؤية أول الثمار", count = "1 ذكر", icon = Icons.Default.Spa),

        // 🤧 العطاس
        CategoryUi(id = 77, title = "دعاء العطاس", subtitle = "الحمد والتشميت عند العطاس", count = "1 ذكر", icon = Icons.Default.AcUnit),
        CategoryUi(id = 78, title = "ما يقال للكافر إذا عطس فحمد الله", subtitle = "الرد على غير المسلم عند العطاس", count = "1 ذكر", icon = Icons.Default.RecordVoiceOver),

        // ❤️ الزواج
        CategoryUi(id = 79, title = "الدعاء للمتزوج", subtitle = "التهنئة والدعاء للعروسين", count = "1 ذكر", icon = Icons.Default.Favorite),
        CategoryUi(id = 80, title = "دعاء المتزوج وشراء الدابة", subtitle = "الدعاء عند الزواج وشراء الدابة", count = "1 ذكر", icon = Icons.Default.FavoriteBorder),
        CategoryUi(id = 81, title = "الدعاء قبل إتيان الزوجة", subtitle = "البسملة والاستعاذة قبل الجماع", count = "1 ذكر", icon = Icons.Default.PrivacyTip),

        // 😡 الغضب والعين
        CategoryUi(id = 82, title = "دعاء الغضب", subtitle = "الاستعاذة من الشيطان عند الغضب", count = "1 ذكر", icon = Icons.Default.LocalFireDepartment),
        CategoryUi(id = 83, title = "دعاء من رأى مبتلى", subtitle = "حمد الله على العافية عند رؤية المبتلى", count = "1 ذكر", icon = Icons.Default.VisibilityOff),

        // 🗣 المجالس
        CategoryUi(id = 84, title = "ما يقال في المجلس", subtitle = "الاستغفار في المجلس مائة مرة", count = "1 ذكر", icon = Icons.Default.Forum),
        CategoryUi(id = 85, title = "كفارة المجلس", subtitle = "ختام المجلس بالتسبيح والاستغفار", count = "1 ذكر", icon = Icons.Default.CleaningServices),

        // 🤝 الأخلاق والمعاملات
        CategoryUi(id = 86, title = "الدعاء لمن قال غفر الله لك", subtitle = "رد الدعاء بالمثل", count = "1 ذكر", icon = Icons.Default.Reply),
        CategoryUi(id = 87, title = "الدعاء لمن صنع إليك معروفاً", subtitle = "الجزاء الحسن لمن أحسن إليك", count = "1 ذكر", icon = Icons.Default.ThumbUp),
        CategoryUi(id = 88, title = "ما يعصم الله به من الدجال", subtitle = "حفظ أوائل الكهف والاستعاذة", count = "1 ذكر", icon = Icons.Default.Security),
        CategoryUi(id = 89, title = "الدعاء لمن قال إني أحبك في الله", subtitle = "الرد على المحبة في الله", count = "1 ذكر", icon = Icons.Default.Favorite),
        CategoryUi(id = 90, title = "الدعاء لمن عرض عليك ماله", subtitle = "الدعاء لمن عرض عليك معروفاً", count = "1 ذكر", icon = Icons.Default.CardGiftcard),
        CategoryUi(id = 91, title = "الدعاء لمن أقرض عند القضاء", subtitle = "شكر المُقرض عند رد الدين", count = "1 ذكر", icon = Icons.Default.Payments),
        CategoryUi(id = 92, title = "دعاء الخوف من الشرك", subtitle = "الاستعاذة من الوقوع في الشرك", count = "1 ذكر", icon = Icons.Default.GppBad),
        CategoryUi(id = 93, title = "الدعاء لمن قال بارك الله فيك", subtitle = "رد الدعاء بالبركة", count = "1 ذكر", icon = Icons.AutoMirrored.Filled.Reply),
        CategoryUi(id = 94, title = "دعاء كراهية الطيرة", subtitle = "التوكل على الله ورد التشاؤم", count = "1 ذكر", icon = Icons.Default.DoNotDisturb),

        // ✈️ السفر
        CategoryUi(id = 95, title = "دعاء الركوب", subtitle = "التسبيح والدعاء عند ركوب المركبة", count = "1 ذكر", icon = Icons.Default.DirectionsCar),
        CategoryUi(id = 96, title = "دعاء السفر", subtitle = "التكبير والدعاء في بداية السفر", count = "1 ذكر", icon = Icons.Default.FlightTakeoff),
        CategoryUi(id = 97, title = "دعاء دخول القرية أو البلدة", subtitle = "طلب الخير والاستعاذة عند دخول المدينة", count = "1 ذكر", icon = Icons.Default.LocationCity),
        CategoryUi(id = 98, title = "دعاء دخول السوق", subtitle = "الذكر عند دخول الأسواق", count = "1 ذكر", icon = Icons.Default.Store),
        CategoryUi(id = 99, title = "الدعاء إذا تعسَ المركوب", subtitle = "البسملة عند تعثر الدابة أو المركبة", count = "1 ذكر", icon = Icons.Default.CarCrash),
        CategoryUi(id = 100, title = "دعاء المسافر للمقيم", subtitle = "وداع المسافر لأهله المقيمين", count = "1 ذكر", icon = Icons.Default.FlightTakeoff),
        CategoryUi(id = 101, title = "دعاء المقيم للمسافر", subtitle = "دعاء المودِّع للمسافر", count = "2 دعاء", icon = Icons.Default.Waves),
        CategoryUi(id = 102, title = "التكبير والتسبيح في سير السفر", subtitle = "التكبير عند الصعود والتسبيح عند النزول", count = "1 ذكر", icon = Icons.Default.Terrain),
        CategoryUi(id = 103, title = "دعاء المسافر إذا أسحر", subtitle = "ما يقوله المسافر في السحر", count = "1 ذكر", icon = Icons.Default.DarkMode),
        CategoryUi(id = 104, title = "الدعاء إذا نزل منزلاً في سفر أو غيره", subtitle = "الاستعاذة عند النزول في مكان جديد", count = "1 ذكر", icon = Icons.Default.Hotel),
        CategoryUi(id = 105, title = "ذكر الرجوع من السفر", subtitle = "التكبير والحمد عند العودة من السفر", count = "1 ذكر", icon = Icons.Default.FlightLand),

        // 😊 الفرح والابتهاج
        CategoryUi(id = 106, title = "ما يقول من أتاه أمر يسره أو يكرهه", subtitle = "الحمد على النعمة والشدة", count = "1 ذكر", icon = Icons.Default.SentimentSatisfied),
        CategoryUi(id = 107, title = "فضل الصلاة على النبي صلى الله عليه وسلم", subtitle = "ثواب الصلاة على النبي ﷺ", count = "5 صيغ", icon = Icons.Default.StarRate),

        // 🤲 السلام
        CategoryUi(id = 108, title = "إفشاء السلام", subtitle = "فضل نشر السلام بين المسلمين", count = "3 أحاديث", icon = Icons.Default.Handshake),
        CategoryUi(id = 109, title = "كيف يرد السلام على الكافر إذا سلَّم", subtitle = "الرد المشروع على غير المسلم", count = "1 ذكر", icon = Icons.Default.RecordVoiceOver),

        // 🐓 الأصوات
        CategoryUi(id = 110, title = "الدعاء عند سماع صياح الديك ونهيق الحمار", subtitle = "سؤال الله وقت صياح الديك", count = "1 ذكر", icon = Icons.Default.VolumeUp),
        CategoryUi(id = 111, title = "دعاء نباح الكلاب بالليل", subtitle = "الاستعاذة عند سماع نباح الكلاب ليلاً", count = "1 ذكر", icon = Icons.Default.Nightlight),

        // 💬 آداب الكلام
        CategoryUi(id = 112, title = "الدعاء لمن سببته", subtitle = "جبر من سبّه المسلم بالدعاء له", count = "1 ذكر", icon = Icons.Default.RecordVoiceOver),
        CategoryUi(id = 113, title = "ما يقول المسلم إذا مدح المسلم", subtitle = "آداب المدح وتحاشي الإطراء", count = "1 ذكر", icon = Icons.Default.ThumbUp),
        CategoryUi(id = 114, title = "ما يقول المسلم إذا زُكِّي", subtitle = "الدعاء حين يمدحك أحد", count = "1 ذكر", icon = Icons.Default.SentimentVerySatisfied),

        // 🕋 الحج والعمرة
        CategoryUi(id = 115, title = "كيف يلبي المحرم في الحج أو العمرة؟", subtitle = "التلبية عند الإحرام بالحج والعمرة", count = "1 ذكر", icon = Icons.Default.Mosque),
        CategoryUi(id = 116, title = "التكبير إذا أتى الركن الأسود", subtitle = "التكبير عند الوصول للحجر الأسود", count = "1 ذكر", icon = Icons.Default.Star),
        CategoryUi(id = 117, title = "الدعاء بين الركن اليماني والحجر الأسود", subtitle = "الدعاء في الطواف بين الركنين", count = "1 ذكر", icon = Icons.Default.ChangeHistory),
        CategoryUi(id = 118, title = "دعاء الوقوف على الصفا والمروة", subtitle = "الذكر على جبلَي الصفا والمروة", count = "1 ذكر", icon = Icons.Default.Landscape),
        CategoryUi(id = 119, title = "الدعاء يوم عرفة", subtitle = "خير الدعاء دعاء يوم عرفة", count = "1 ذكر", icon = Icons.Default.WbSunny),
        CategoryUi(id = 120, title = "الذكر عند المشعر الحرام", subtitle = "الذكر والدعاء في مزدلفة", count = "1 ذكر", icon = Icons.Default.NightsStay),
        CategoryUi(id = 121, title = "التكبير عند رمي الجمار مع كل حصاة", subtitle = "التكبير والوقوف بعد رمي الجمار", count = "1 ذكر", icon = Icons.Default.Circle),

        // 😲 التعجب والشكر
        CategoryUi(id = 122, title = "دعاء التعجب والأمر السار", subtitle = "سبحان الله والله أكبر للتعجب", count = "2 ذكر", icon = Icons.Default.Celebration),
        CategoryUi(id = 123, title = "ما يفعل من أتاه أمر يسره", subtitle = "سجدة الشكر عند النعمة", count = "1 ذكر", icon = Icons.Default.EmojiEvents),

        // 🩺 ألم الجسد
        CategoryUi(id = 124, title = "ما يقول من أحس وجعاً في جسده", subtitle = "وضع اليد والاستعاذة من الألم", count = "1 ذكر", icon = Icons.Default.MedicalServices),
        CategoryUi(id = 125, title = "دعاء من خشي أن يصيب شيئاً بعينه", subtitle = "الدعاء بالبركة لدفع إصابة العين", count = "1 ذكر", icon = Icons.Default.RemoveRedEye),

        // ⚡ الفزع والذبح
        CategoryUi(id = 126, title = "ما يقال عند الفزع", subtitle = "لا إله إلا الله عند المفاجأة", count = "1 ذكر", icon = Icons.Default.FlashOn),
        CategoryUi(id = 127, title = "ما يقول عند الذبح أو النحر", subtitle = "البسملة والتكبير عند التضحية", count = "1 ذكر", icon = Icons.Default.Agriculture),
        CategoryUi(id = 128, title = "ما يقول لرد كيد مردة الشياطين", subtitle = "الاستعاذة من شر الخلق كله", count = "1 ذكر", icon = Icons.Default.Block),

        // 🌟 الاستغفار والتسبيح
        CategoryUi(id = 129, title = "الاستغفار والتوبة", subtitle = "الاستغفار في اليوم مئة مرة", count = "6 صيغ", icon = Icons.Default.Refresh),
        CategoryUi(id = 130, title = "فضل التسبيح والتحميد والتهليل والتكبير", subtitle = "الباقيات الصالحات وفضلها العظيم", count = "12 صيغة", icon = Icons.Default.AutoAwesome),
        CategoryUi(id = 131, title = "كيف كان النبي يسبح؟", subtitle = "عقد التسبيح باليمين اقتداءً بالنبي ﷺ", count = "1 ذكر", icon = Icons.Default.RadioButtonChecked),
        CategoryUi(id = 132, title = "من أنواع الخير والآداب الجامعة", subtitle = "آداب إسلامية جامعة من السنة النبوية", count = "1 ذكر", icon = Icons.Default.VolunteerActivism),
    )


}