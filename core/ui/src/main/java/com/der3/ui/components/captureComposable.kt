package com.der3.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Captures a Composable as a Bitmap.
 *
 * This function works by creating a [ComposeView], attaching it to the Activity's root view
 * (to ensure it has a window and a Recomposer), measuring it, and drawing it to a Canvas.
 *
 * This must be called from a coroutine, preferably on the Main thread.
 */
suspend fun captureComposable(
    context: Context,
    content: @Composable () -> Unit,
): Bitmap =
    withContext(AndroidUiDispatcher.Main) {
        // Create a ComposeView to host our content ..... configure compose view
        val composeView =
            ComposeView(context).apply {
                // when the ViewTree is destroyed, the corresponding to Compose components are also cleaned up from memory
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            }

        val density = context.resources.displayMetrics.density

        // Fixed width (e.g., 420dp) for consistent sharing quality
        val widthPx = (420 * density).toInt().coerceAtLeast(1)

        // Allow height to be determined by content (wrap_content equivalent)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY)

        composeView.setContent {
            content()
        }

        /**
         * ComposeView needs to be attached to a window to find a Recomposer and
         * perform composition/layout. We use a temporary FrameLayout and attach it
         * to the Activity's content view
         */
        val parent = FrameLayout(context)
        val root =
            if (context is android.app.Activity) {
                context.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            } else {
                null
            }

        root?.addView(parent)
        parent.addView(composeView)

        // Copy the lifecycle and saved state owners to the temporary view
        root?.let {
            composeView.setViewTreeLifecycleOwner(it.findViewTreeLifecycleOwner())
            composeView.setViewTreeViewModelStoreOwner(it.findViewTreeViewModelStoreOwner())
            composeView.setViewTreeSavedStateRegistryOwner(it.findViewTreeSavedStateRegistryOwner())
        }

        /**
         * Wait for composition and layout. We wait for multiple frames and a small delay
         * to ensure that all resources (like images) have started rendering.
         */
        awaitFrame()
        composeView.measure(widthSpec, heightSpec)
        composeView.layout(0, 0, widthPx, composeView.measuredHeight)
        delay(100) // Small delay to allow recomposition and image loading to settle
        awaitFrame()

        val measuredHeight = composeView.measuredHeight.coerceAtLeast(1)
        composeView.layout(0, 0, widthPx, measuredHeight)

        // Create the Bitmap with the measured dimensions
        val bitmap = createBitmap(widthPx, measuredHeight)

        // Draw the ComposeView's content onto the Bitmap's Canvas
        val canvas = Canvas(bitmap)
        composeView.draw(canvas)

        // Clean up by removing the temporary views from the hierarchy
        parent.removeView(composeView)
        root?.removeView(parent)

        return@withContext bitmap
    }

/**
 * Saves a Bitmap to the cache directory and returns a content URI.
 *
 * This uses [FileProvider] to create a secure content:// URI for sharing.
 */
fun saveBitmapToCache(
    context: Context,
    bitmap: Bitmap,
): Uri? =
    try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_zekr_${System.currentTimeMillis()}.png")

        // Write the bitmap to the file
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        // Return a content URI using FileProvider defined in AndroidManifest.xml
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
