package com.der3.muslim

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.der3.muslim.fcm.MessagingServiceConfig
import com.der3.muslim.main_screen.MainScreen
import com.der3.ui.themes.Der3MuslimTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 2. إعداد FCM والاشتراك في المواضيع
        MessagingServiceConfig.setupFCM()

        enableEdgeToEdge()
        setContent {
            Der3MuslimTheme(
                language = Locale.Builder().setLanguage("ar").build()
            ) {
                MainScreen()
            }
        }
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        // إذا ضغط المستخدم على تنبيه وكان التطبيق مفتوحاً، يمكنك معالجة البيانات هنا
    }
}
