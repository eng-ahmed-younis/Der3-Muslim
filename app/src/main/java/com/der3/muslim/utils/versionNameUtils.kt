package com.der3.muslim.utils

import android.content.Context
import android.os.Build
import android.content.pm.PackageManager

fun Context.getAppVersionName(): String? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(0)
            ).versionName
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, 0).versionName
        }
    } catch (e: Exception) {
        "unknown"
    }
}