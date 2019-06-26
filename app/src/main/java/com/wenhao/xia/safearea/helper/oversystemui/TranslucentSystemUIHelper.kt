package com.wenhao.xia.safearea.helper.oversystemui

import android.annotation.TargetApi
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.wenhao.xia.safearea.R
import com.wenhao.xia.safearea.SafeArea
import com.wenhao.xia.safearea.SafeArea.Edge.*
import kotlinx.android.synthetic.main.activity_main.view.*

@TargetApi(Build.VERSION_CODES.KITKAT)
class TranslucentSystemUIHelper : OverSystemUIHelper {
    var safeAreaProvider: SafeAreaProvider? = null

    private fun getSafeArea(viewToFindDefaultSafeArea: View?, edge: SafeArea.Edge): SafeArea? {
        val safeAreaByProvider = safeAreaProvider?.getSafeArea(edge)
        if (safeAreaByProvider != null) {
            return safeAreaByProvider
        }

        if (viewToFindDefaultSafeArea == null) {
            return null
        }

        val safeAreaIDToFind = when (edge) {
            TOP -> R.id.safeAreaTop
            BOTTOM -> R.id.safeAreaBottom
            LEFT -> R.id.safeAreaLeft
            RIGHT -> R.id.safeAreaRight
        }
        return viewToFindDefaultSafeArea.findViewById(safeAreaIDToFind)
    }

    override fun setOverStatusBar(window: Window, over: Boolean) {
        setLayoutUnderStatusBar(window, over)

        if (over) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    override fun setOverNavigationBar(window: Window, over: Boolean) {
        setLayoutUnderNavigationBar(window, over)

        if (over) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    fun setStatusBarBackgroundColorNow(window: Window, color: Int) {
        val safeArea = getSafeArea(window.decorView, TOP)
        safeArea?.setBackgroundColor(color)
    }

    override fun setStatusBarBackgroundColor(window: Window, color: Int) {
        //if in this mode we just change the safe area background color, So we post it to next Message handle
        // in mainlooper is case of calling this method before setContentView
        Handler(Looper.getMainLooper())
            .post { setStatusBarBackgroundColorNow(window, color) }
    }

    fun setNavigationBarBackgroundColorNow(window: Window, color: Int) {
        val safeArea = getSafeArea(window.decorView, BOTTOM)
        safeArea?.setBackgroundColor(color)
    }

    override fun setNavigationBarBackgroundColor(window: Window, color: Int) {
        //if in this mode we just change the safe area background color, So we post it to next Message handle
        // in mainlooper is case of calling this method before setContentView
        Handler(Looper.getMainLooper())
            .post { setNavigationBarBackgroundColorNow(window, color) }
    }

    override fun setStatusBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        throw IllegalStateException("Can not set displayMode in translucent Mode.")
    }

    override fun setNavigationBarDisplayMode(window: Window, displayMode: OverSystemUIHelper.SystemUiDisplayMode) {
        throw IllegalStateException("Can not set displayMode in translucent Mode.")
    }

    interface SafeAreaProvider {
        fun getSafeArea(direction: SafeArea.Edge): SafeArea?
    }

}