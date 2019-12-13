package com.wxx.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Scroller

/**
 * @author ：wuxinxi on 2019/11/22 .
 * @packages ：com.wxx.view.custom .
 * TODO:一句话描述
 */
class CustomMoveView2 : View ,View.OnClickListener{
    override fun onClick(v: View?) {
        smoothScrollTo(-400,0)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(
        context,
        attributeSet
    )

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )
    init {
        setOnClickListener(this)
    }

    var mScroller: Scroller = Scroller(context)

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            val view = parent as View
            view.scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }


    public fun smoothScrollTo(destX: Int, destY: Int) {
        val scrollX = scrollX
        val delta = destX - scrollX
        mScroller.startScroll(scrollX, 0, delta,0,200)
        invalidate()
    }
}