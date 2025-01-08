package net.micg.plantcare.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

object InsetsUtils {
    fun addTopInsetsMarginToCurrentView(view: View) {
        val currentElementBottomMargin = view.marginTop
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = currentElementBottomMargin + insets.top
            }
            windowInsets
        }
    }

    fun addBottomInsetsMarginToCurrentView(view: View) {
        val currentElementBottomMargin = view.marginBottom
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = currentElementBottomMargin + insets.bottom
            }
            windowInsets
        }
    }

    fun addBottomInsetsPaddingToCurrentView(view: View) {
        val currentElementBottomPadding = view.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                bottom = currentElementBottomPadding + insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    fun addTopInsetsPaddingToCurrentView(view: View) {
        val currentElementTopPadding = view.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = currentElementTopPadding + insets.top
            )
            windowInsets
        }
    }
}
