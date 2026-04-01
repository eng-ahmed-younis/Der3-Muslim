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
import androidx.compose.runtime.collectAsState
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
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        viewModel.handleIntent(intent)

        setContent {
            Der3MuslimTheme(
                style = viewModel.appStyleFlow.collectAsState().value,
                language = Locale.Builder().setLanguage("ar").build()
            ) {
                MainScreen()
            }
        }
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("MainActivity_onNewIntent", "onNewIntent: ${intent.data}")
        setIntent(intent)
        viewModel.handleIntent(intent)
    }
}
