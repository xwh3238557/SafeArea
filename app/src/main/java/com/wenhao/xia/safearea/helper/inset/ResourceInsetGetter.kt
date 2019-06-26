package com.wenhao.xia.safearea.helper.inset

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View

class ResourceInsetGetter : InsetGetter {
    override fun getWindowInsets(view: View, onInsetGetListener: InsetGetter.OnInsetGetListener) {
        val context = view.context
        val inset = getWindowInsets(context)
        onInsetGetListener.onInsetChange(inset)
    }


    fun getWindowInsets(context: Context): Rect {
        val statusBarHeightIdentifier = context.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        val navigationBarHeightIdentifier = context.resources.getIdentifier(
            "navigation_bar_height", "dimen", "android"
        )
        return Rect().apply {
            this.top = try {
                context.resources.getDimensionPixelSize(statusBarHeightIdentifier)
            } catch (e: Resources.NotFoundException) {
                0
            }
            this.bottom = try {
                context.resources.getDimensionPixelSize(navigationBarHeightIdentifier)
            } catch (e: Resources.NotFoundException) {
                0
            }
            this.left = 0
            this.right = 0
        }
    }

}