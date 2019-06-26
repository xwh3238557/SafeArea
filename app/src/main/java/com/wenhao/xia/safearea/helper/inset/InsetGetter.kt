package com.wenhao.xia.safearea.helper.inset

import android.graphics.Rect
import android.view.View

interface InsetGetter {
    fun getWindowInsets(view: View, onInsetGetListener: OnInsetGetListener)

    interface OnInsetGetListener {
        fun onInsetChange(insetRect: Rect)
    }
}