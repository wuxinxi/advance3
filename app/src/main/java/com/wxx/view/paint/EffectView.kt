package com.wxx.view.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author ：wuxinxi on 2019/12/3 .
 * @packages ：com.wxx.view.paint .
 * TODO:一句话描述
 */
class EffectView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        paint.strokeWidth = 2f
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE


        //圆角曲线
        //val effect=CornerPathEffect(50f)

        //离散
        //val effect=DiscretePathEffect(2f,1f)

        //虚线（array：[画线长度、空白长度]必须是成对出现），phase：偏移量
        //val effect=DashPathEffect(floatArrayOf(20f,10f),0f)

        /**
         * Path shape
         * float advance
         * float phase
         * Style style
         */
        val deshPath = Path()
        //val effect = PathDashPathEffect(deshPath, 40f, 0f, PathDashPathEffect.Style.TRANSLATE)

        //组合
        val dashPathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
        val discretePathEffect = DiscretePathEffect(20f, 5f)
        /*
        PathEffect outerpe,  后执行
        PathEffect innerpe 先执行应用
         */
        val effect = ComposePathEffect(dashPathEffect, discretePathEffect)
        paint.pathEffect = effect
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.moveTo(10f, 10f)
        path.rLineTo(100f, 200f)
        path.lineTo(150f, 20f)
        path.lineTo(300f, 200f)
        path.lineTo(400f, 20f)

        canvas.drawPath(path, paint)

    }
}