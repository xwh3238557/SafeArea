package com.wenhao.xia.safearea

import android.content.Context
import android.graphics.Rect
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class SafeArea
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    var edge: Edge = Edge.TOP
        set(value) {
            field = value
            refreshLayoutParams()
        }

    private var insetRect: Rect? = null
        set(value) {
            field = value
            if (field != null) refreshLayoutParams()
        }

    private fun refreshLayoutParams() {
        if (isInEditMode) return

        val width: Int
        val height: Int
        when (edge) {
            Edge.TOP -> {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = insetRect?.top ?: 0
            }

            Edge.BOTTOM -> {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = insetRect?.bottom ?: 0
            }

            Edge.LEFT -> {
                height = insetRect?.left ?: 0
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }

            Edge.RIGHT -> {
                height = insetRect?.right ?: 0
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }

        layoutParams = layoutParams?.apply {
            this.height = height
            this.width = width
        } ?: ViewGroup.LayoutParams(width, height);
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SafeArea)
        this.edge = getEdgeFromValue(typedArray.getInt(R.styleable.SafeArea_edgeDirection, Edge.TOP.value)) ?: Edge.TOP
        typedArray.recycle()

        setWillNotDraw(true)

        if (isInEditMode) {
            layoutParams = when (edge) {
                Edge.TOP, Edge.BOTTOM -> {
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
                }
                Edge.LEFT, Edge.RIGHT -> {
                    ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->
                this.insetRect = Rect(
                    windowInsets.systemWindowInsetLeft,
                    windowInsets.systemWindowInsetTop,
                    windowInsets.systemWindowInsetRight,
                    windowInsets.systemWindowInsetBottom
                )

                when (edge) {
                    Edge.TOP -> {
                        windowInsets.systemWindowInsetLeft
                        windowInsets.replaceSystemWindowInsets(
                            windowInsets.systemWindowInsetLeft,
                            0,
                            windowInsets.systemWindowInsetRight,
                            windowInsets.systemWindowInsetBottom
                        )
                    }

                    Edge.BOTTOM ->
                        windowInsets.replaceSystemWindowInsets(
                            windowInsets.systemWindowInsetLeft,
                            windowInsets.systemWindowInsetTop,
                            windowInsets.systemWindowInsetRight,
                            0
                        )
                    Edge.LEFT ->
                        windowInsets.replaceSystemWindowInsets(
                            0,
                            windowInsets.systemWindowInsetTop,
                            windowInsets.systemWindowInsetRight,
                            windowInsets.systemWindowInsetBottom
                        )
                    Edge.RIGHT ->
                        windowInsets.replaceSystemWindowInsets(
                            windowInsets.systemWindowInsetLeft,
                            windowInsets.systemWindowInsetTop,
                            0,
                            windowInsets.systemWindowInsetBottom
                        )
                }
                windowInsets
            }
        }
    }

    companion object {
        private fun getEdgeFromValue(value: Int): Edge? {
            return when (value) {
                Edge.TOP.value -> Edge.TOP
                Edge.BOTTOM.value -> Edge.BOTTOM
                Edge.LEFT.value -> Edge.LEFT
                Edge.RIGHT.value -> Edge.RIGHT
                else -> null
            }
        }
    }

    enum class Edge(val value: Int) {
        TOP(1), BOTTOM(2), LEFT(3), RIGHT(4)
    }
}