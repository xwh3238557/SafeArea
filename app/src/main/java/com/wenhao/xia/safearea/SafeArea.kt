package com.wenhao.xia.safearea

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.wenhao.xia.safearea.helper.inset.AppCompatInsetGetter
import com.wenhao.xia.safearea.helper.inset.InsetGetter

class SafeArea
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private val insetGetter by lazy {
        AppCompatInsetGetter()
    }

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

        getWindowInsets(object : InsetGetter.OnInsetGetListener {
            override fun onInsetChange(insetRect: Rect) {
                this@SafeArea.insetRect = insetRect
            }
        })
    }

    private fun getWindowInsets(listener: InsetGetter.OnInsetGetListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //we can only draw views under system uis from kitkat
            insetGetter.getWindowInsets(this, listener)
        } else {
            listener.onInsetChange(Rect(0, 0, 0, 0))
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