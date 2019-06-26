package com.wenhao.xia.safearea.helper.oversystemui

import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

interface OverSystemUIHelper {
    fun setOverStatusBar(window: Window, over: Boolean)

    fun setOverNavigationBar(window: Window, over: Boolean)

    fun setOverSystemUi(window: Window, over: Boolean) {
        setOverStatusBar(window, over)
        setOverNavigationBar(window, over)
    }

    fun setLayoutUnderStatusBar(window: Window, over: Boolean) {
        val systemUiVisibility = window.decorView.systemUiVisibility

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = if (over)
                systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            else
                systemUiVisibility xor View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun setLayoutUnderNavigationBar(window: Window, over: Boolean) {
        val systemUiVisibility = window.decorView.systemUiVisibility

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = if (over)
                systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            else
                systemUiVisibility xor View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    fun setStatusBarBackgroundColor(window: Window, @ColorInt color: Int)

    fun setStatusBarBackgroundColorResource(window: Window, context: Context, @ColorRes colorRes: Int) {
        setStatusBarBackgroundColor(window, ContextCompat.getColor(context, colorRes))
    }

    fun setNavigationBarBackgroundColor(window: Window, @ColorInt color: Int)

    fun setNavigationBarBackgroundColorResource(window: Window, context: Context, @ColorRes colorRes: Int) {
        setNavigationBarBackgroundColor(window, ContextCompat.getColor(context, colorRes))
    }

    fun setStatusBarDisplayMode(window: Window, displayMode: SystemUiDisplayMode)

    fun setNavigationBarDisplayMode(window: Window, displayMode: SystemUiDisplayMode)

    sealed class SystemUiDisplayMode {
        object Light : SystemUiDisplayMode()
        object Dark : SystemUiDisplayMode()
    }
}