package com.wenhao.xia.safearea.helper.oversystemui

import android.os.Build
import android.view.Window

class AppCompatOverSystemUIHelper : OverSystemUIHelper  {
    private val REAL_HELPER by lazy {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> AndroidOverSystemUIHelper()
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> TranslucentSystemUIHelper()
            else -> null
        }
    }

    override fun setOverStatusBar(window: Window, over: Boolean) {
        REAL_HELPER?.setOverStatusBar(window, over)
    }

    override fun setOverNavigationBar(window: Window, over: Boolean) {
        REAL_HELPER?.setOverNavigationBar(window, over)
    }

    override fun setStatusBarBackgroundColor(window: Window, color: Int) {
        REAL_HELPER?.setStatusBarBackgroundColor(window, color)
    }

    override fun setNavigationBarBackgroundColor(window: Window, color: Int) {
        REAL_HELPER?.setNavigationBarBackgroundColor(window, color)
    }

    override fun setStatusBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        REAL_HELPER?.setStatusBarDisplayMode(window, displayMode)
    }

    override fun setNavigationBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        REAL_HELPER?.setNavigationBarDisplayMode(window, displayMode)
    }
}