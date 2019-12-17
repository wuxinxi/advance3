package com.wxx.view.advance.group

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author ：wuxinxi on 2019/12/16 .
 * @packages ：com.wxx.view.advance.group .
 * TODO:自定义LinearLayout
 */
class MyLinearLayout : ViewGroup {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet!!, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    /**
     * 实现测量子view大小以及设定viewGroup的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //将所有的子view进行测量，这个会触发每个字view的onMeasure()方法
        //区分measureChild measureChild是对每个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val childCount = childCount
        if (childCount == 0) {
            //如果没有子View
            setMeasuredDimension(0, 0)
        } else {
            //如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                val height = getChildSumHeight()
                val width = getMaxWidth()
                setMeasuredDimension(width, height)
            } else if (heightMode == MeasureSpec.AT_MOST) {
                //如果只有高度是包裹内容wrap_content
                setMeasuredDimension(widthSize, getChildSumHeight())
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //如果只有宽度是包裹内容wrap_content
                setMeasuredDimension(getMaxWidth(), heightSize)
            }
        }

    }

    /**
     * 获取子view最大的宽度
     */
    private fun getMaxWidth(): Int {
        val childCount = childCount
        var maxWidth = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.measuredWidth > maxWidth) {
                maxWidth = childView.measuredWidth
            }
        }
        return maxWidth
    }

    /**
     * 获取子view高度之和
     */
    private fun getChildSumHeight(): Int {
        val childCount = childCount
        var height = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            height += childView.measuredHeight
        }
        return height
    }

    /**
     *摆放子View
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childCount = childCount
        //当前位置的高度
        var curHeight = top
        //摆放view
        for (i in 0 until childCount) {
            val viewChild = getChildAt(i)
            val height = viewChild.measuredHeight
            val width = viewChild.measuredWidth
            viewChild.layout(left, curHeight, left + width, curHeight + height)
            curHeight += height
        }
    }


}