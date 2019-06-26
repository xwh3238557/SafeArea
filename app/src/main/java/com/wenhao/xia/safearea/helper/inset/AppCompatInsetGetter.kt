package com.wenhao.xia.safearea.helper.inset

import android.os.Build
import android.view.View

class AppCompatInsetGetter : InsetGetter {
    private val realGetter by lazy {
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP -> WindowInsetGetter()

            else -> ResourceInsetGetter()
        }
    }

    override fun getWindowInsets(view: View, onInsetGetListener: InsetGetter.OnInsetGetListener) {
        realGetter.getWindowInsets(view, onInsetGetListener)
    }
}