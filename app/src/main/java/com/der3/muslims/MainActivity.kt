package com.der3.muslims

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.der3.muslims.main_screen.MainScreen
import com.der3.ui.themes.Der3MuslimTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

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
