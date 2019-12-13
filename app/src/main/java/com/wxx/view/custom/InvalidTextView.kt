package com.wxx.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView



/**
 * @author ：wuxinxi on 2019/11/26 .
 * @packages ：com.wxx.view.custom .
 * TODO:无效TextView(具有删除线)
 */
class InvalidTextView : TextView {

    private val paint = Paint()

    constructor(context: Context) : super(context)

    constructor(context: Context, attribute: AttributeSet) : super(context, attribute)

    constructor(context: Context, attribute: AttributeSet, defStyle: Int) : super(context, attribute, defStyle)

    init {
        paint.color = Color.RED
        paint.strokeWidth = 3.5f
        paint.pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        canvas.drawLine(0f, height / 2.toFloat(), width.toFloat(), height / 2.toFloat(), paint)
    }


}