package com.wenhao.xia.safearea

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class SafeArea
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    var edge: Edge = Edge.TOP
        set(value) {
            field = value
            requestLayout()
        }

    private var insetRect: Rect? = null
        set(value) {
            field = value
            requestLayout()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h: Int
        val w: Int

        when (edge) {
            Edge.BOTTOM -> {
                w = widthMeasureSpec
                h = MeasureSpec.makeMeasureSpec(insetRect?.bottom ?: 0, MeasureSpec.EXACTLY)
            }

            Edge.TOP -> {
                w = widthMeasureSpec
                h = MeasureSpec.makeMeasureSpec(insetRect?.top ?: 0, MeasureSpec.EXACTLY)
            }

            Edge.LEFT -> {
                w = MeasureSpec.makeMeasureSpec(insetRect?.left ?: 0, MeasureSpec.EXACTLY)
                h = heightMeasureSpec
            }

            Edge.RIGHT -> {
                w = MeasureSpec.makeMeasureSpec(insetRect?.right ?: 0, MeasureSpec.EXACTLY)
                h = heightMeasureSpec
            }
        }

        setMeasuredDimension(w, h)
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SafeArea)
        this.edge = getEdgeFromValue(typedArray.getInt(R.styleable.SafeArea_edgeDirection, Edge.TOP.value)) ?: Edge.TOP
        typedArray.recycle()

        setWillNotDraw(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val statusBarHeightIdentifier = context.resources.getIdentifier(
                "status_bar_height", "dimen", "android"
            )
            val navigationBarHeightIdentifier = context.resources.getIdentifier(
                "navigation_bar_height", "dimen", "android"
            )
            val inset = Rect()
            inset.top = context.resources.getDimensionPixelSize(statusBarHeightIdentifier)
            inset.bottom = context.resources.getDimensionPixelSize(navigationBarHeightIdentifier)

            insetRect = inset
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