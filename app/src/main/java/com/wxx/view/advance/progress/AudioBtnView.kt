package com.wxx.view.advance.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author ：wuxinxi on 2019/12/13 .
 * @packages ：com.wxx.view.advance.progress .
 * TODO:音频按钮：播放，暂停
 */
class AudioBtnView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet!!, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init(context, attributeSet)
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val btnPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val rectF = RectF()

    var progressColor = Color.RED
    var btnColor = Color.RED

    var viewWidth = 400
    var viewHeight = 400
    var phoneWidth = 0
    var centerX = 0f
    var centerY = 0f
    var radius = 0f
    var space = 50f

    var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    private fun init(context: Context, attributeSet: AttributeSet) {
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f

        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = 20f

        btnPaint.color = btnColor
        btnPaint.style = Paint.Style.FILL

        val displayMetrics = context.resources.displayMetrics
        phoneWidth = displayMetrics.widthPixels
        centerX = phoneWidth / 2f
        centerY = space + viewHeight / 2
        radius = viewWidth / 2f

        rectF.set(phoneWidth / 2 - radius, space, phoneWidth / 2 + radius, viewHeight + space)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, radius, paint)
        canvas.drawArc(rectF,progress,90f,false,progressPaint)

        drawPlay(canvas)

    }

    private fun drawPlay(canvas: Canvas){

    }
}