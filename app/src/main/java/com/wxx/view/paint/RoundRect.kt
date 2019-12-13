package com.wxx.view.paint

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.wxx.view.R

/**
 * @author ：wuxinxi on 2019/12/2 .
 * @packages ：com.wxx.view.paint .
 * TODO:一句话描述
 */
class RoundRect : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.RED

        //着色器
        //线性渐变
        val shader1 = LinearGradient(300f, 300f, 500f, 400f, Color.RED, Color.BLUE, Shader.TileMode.CLAMP)
        //辐射渐变
        //val shader2=RadialGradient(300f,300f,500f,Color.RED,Color.BLUE,Shader.TileMode.CLAMP)
        //扫描渐变
        //val shader3 = SweepGradient(300f, 300f, Color.RED, Color.BLUE)
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic)
        //bitmap 着色
        val shader4 = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //组合
        val shader = ComposeShader(shader1, shader4, PorterDuff.Mode.SRC_OVER)
        paint.shader = shader

        path.addCircle(300f, 300f, 200f, Path.Direction.CCW)
        path.addCircle(500f, 300f, 200f, Path.Direction.CCW)
        path.fillType = Path.FillType.EVEN_ODD


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制圆角矩形
        //canvas.drawRoundRect(20f, 20f, 300f, 150f, 30f, 30f, paint)

        canvas.drawPath(path, paint)


    }
}