package com.der3.muslim.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.der3.shared.service.notification.NotificationPermission
import com.der3.ui.R
import com.der3.ui.themes.AppColors

/**
 * Handles the notification permission flow:
 * 1- Open app: Check current notification permission status.
 * 2- Request: If not granted (Android 13+), launch system permission request.
 * 3- Denial: If system request is denied, show custom localized AlertDialog.
 * 4- Options: User can "Go to Settings" or "Close App".
 * 5- Settings: Redirect user to system app settings.
 * 6- Return: On return from settings, check permission again in ON_RESUME.
 * 7- Result: If still not granted after returning from settings, close the app automatically.
 */
@Composable
fun NotificationPermissionLauncher() {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var showDialog by remember { mutableStateOf(false) }
    var wentToSettings by remember { mutableStateOf(false) }

    // 2- Request: System permission request launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // 3- Denial: If denied, trigger the custom dialog
        if (!isGranted) {
            showDialog = true
        }
    }

    // 6- Return: Observer to check permission when user returns from settings
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionStatus =
                        NotificationPermission.checkNotificationPermission(context)
                    if (permissionStatus == NotificationPermission.PermissionStatus.GRANTED) {
                        showDialog = false
                        wentToSettings = false
                    } else if (wentToSettings) {
                        // 7- Result: Close app if still not granted after return
                        (context as? Activity)?.finish()
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // 4- Options: The custom Arabic dialog
    if (showDialog) {
        AlertDialog(
            shape = RoundedCornerShape(24.dp),
            onDismissRequest = { /* Force a choice */ },
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    color = AppColors.green800,
                    fontWeight = FontWeight.W700,
                    text = stringResource(R.string.notification_permission_title),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    color = AppColors.gray900Text,
                    text = stringResource(R.string.notification_permission_message),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                // 5- Settings: Open system settings
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.green800,
                        contentColor = Color.White
                    ),
                    onClick = {
                        wentToSettings = true
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.notification_permission_settings),
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                // 4- Options: Close app if user cancels
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        showDialog = false
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.notification_permission_cancel),
                        color = AppColors.green800,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = Color.White
        )
    }

    // 1- Open app: Initial check when the component enters the composition
    LaunchedEffect(Unit) {
        val permissionStatus = NotificationPermission.checkNotificationPermission(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            permissionStatus != NotificationPermission.PermissionStatus.GRANTED
        ) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
