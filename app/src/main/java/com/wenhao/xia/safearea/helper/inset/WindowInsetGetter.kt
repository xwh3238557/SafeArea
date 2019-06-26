package com.wenhao.xia.safearea.helper.inset

import android.annotation.TargetApi
import android.graphics.Rect
import android.os.Build
import android.view.View

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class WindowInsetGetter : InsetGetter {
    override fun getWindowInsets(view: View, onInsetGetListener: InsetGetter.OnInsetGetListener) {
        view.setOnApplyWindowInsetsListener {  _, windowInset ->
            val insetRect = Rect(
                windowInset.systemWindowInsetLeft,
                windowInset.systemWindowInsetTop,
                windowInset.systemWindowInsetRight,
                windowInset.systemWindowInsetBottom
            )
            onInsetGetListener.onInsetChange(insetRect)

            windowInset
        }
    }

}