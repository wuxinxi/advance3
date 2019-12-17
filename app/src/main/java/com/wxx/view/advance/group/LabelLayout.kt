package com.wxx.view.advance.group

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

/**
 * @author ：wuxinxi on 2019/12/16 .
 * @packages ：com.wxx.view.advance.group .
 * TODO:标签layout
 */
class LabelLayout : ViewGroup {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet!!, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet) {
        init()
    }

    var viewWidth = 0
    private fun init() {
        val displayMetrics = context.resources.displayMetrics
        viewWidth = displayMetrics.widthPixels

    }

    var maxWidth = 0
    var maxHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec,heightMeasureSpec)
        val childCount = childCount
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        }
        //总共多少行
        var lines = 1
        var lineWidth = 0
        maxWidth = 0
        maxHeight = 0
        var childWidth: Int
        var childHeight= 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val lp = childView.layoutParams as MarginLayoutParams
            childWidth = childView.measuredWidth + lp.rightMargin + lp.leftMargin
            childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            lineWidth += childWidth
            if (lineWidth >= widthSize) {
                lines++
                lineWidth = childWidth
                maxWidth = widthSize
            }
        }
        maxHeight = childHeight * lines
        Log.d("tag", "lines=$lines,childCount=$childCount")

        setSelfSize(widthSpec, widthSize, heightSpec, heightSize)
    }

    private fun setSelfSize(widthMeasureSpec: Int, widthSize: Int, heightMeasureSpec: Int, heightSize: Int) {
        var finalWidth = 0
        var finalHeight = 0
        when (widthMeasureSpec) {
            MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> finalWidth = widthSize
            MeasureSpec.AT_MOST -> finalWidth = maxWidth
        }
        when (heightMeasureSpec) {
            MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> finalHeight = heightSize
            MeasureSpec.AT_MOST -> finalHeight = maxHeight
        }

        Log.d("tag", "finalWidth=$finalWidth,finalHeight=$finalHeight")
        setMeasuredDimension(finalWidth, finalHeight)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        val width=width
        var lines = 0
        var lineWidth = 0
        var lineHeight = 0
        for (i in 0 until childCount) {
            val viewChild = getChildAt(i)
            val lp = viewChild.layoutParams as MarginLayoutParams
            lineWidth += viewChild.measuredWidth + lp.leftMargin + lp.rightMargin
            if (lineWidth >= width) {
                lines++
                lineWidth = viewChild.measuredWidth + lp.leftMargin + lp.rightMargin
                lineHeight = lines * (viewChild.measuredHeight + lp.bottomMargin + lp.topMargin)
            }
            viewChild.layout(
                lineWidth - viewChild.measuredWidth - lp.rightMargin,
                lineHeight + lp.topMargin,
                lineWidth - lp.rightMargin,
                lineHeight + lp.topMargin + viewChild.measuredHeight
            )
        }

    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs!!)
    }
}