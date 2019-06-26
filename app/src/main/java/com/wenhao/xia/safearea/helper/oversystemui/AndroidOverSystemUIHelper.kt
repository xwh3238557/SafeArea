package com.wenhao.xia.safearea.helper.oversystemui

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AndroidOverSystemUIHelper : OverSystemUIHelper {
    override fun setOverStatusBar(window: Window, over: Boolean) {
        setLayoutUnderStatusBar(window, over)

        if (over) {
            setStatusBarBackgroundColor(window, Color.TRANSPARENT)
        } else {
            setStatusBarBackgroundColor(window, Color.BLACK)
        }
    }

    override fun setOverNavigationBar(window: Window, over: Boolean) {
        setLayoutUnderNavigationBar(window, over)

        if (over) {
            setNavigationBarBackgroundColor(window, Color.TRANSPARENT)
        } else {
            setNavigationBarBackgroundColor(window, Color.BLACK)
        }
    }

    override fun setStatusBarBackgroundColor(window: Window, color: Int) {
        window.statusBarColor = color
    }

    override fun setNavigationBarBackgroundColor(window: Window, color: Int) {
        window.navigationBarColor = color
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setStatusBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        val currSystemUIVisibility = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = when (displayMode) {
            OverSystemUIHelper.SystemUiDisplayMode.Light -> currSystemUIVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            OverSystemUIHelper.SystemUiDisplayMode.Dark -> currSystemUIVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun setNavigationBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        val currSystemUIVisibility = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = when (displayMode) {
            OverSystemUIHelper.SystemUiDisplayMode.Light -> currSystemUIVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            OverSystemUIHelper.SystemUiDisplayMode.Dark -> currSystemUIVisibility xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}