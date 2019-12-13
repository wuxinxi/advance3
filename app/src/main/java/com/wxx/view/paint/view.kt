package com.wxx.view.paint

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

/**
 * @author ：wuxinxi on 2019/12/2 .
 * @packages ：com.wxx.view.paint .
 * TODO:
 * 1. Canvas.drawXXX()
 * 2. Paint()
 * 3. Canvas绘制的辅助：几何变换
 * 4. 控制绘制顺序
 */
fun main() {
    //抗锯齿ANTI_ALIAS_FLAG
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.style = Paint.Style.FILL

    val path= Path()

    val canvas = Canvas()
    //绘制圆点坐标为（300,300），半径为200的圆
    canvas.drawCircle(300f, 300f, 200f, paint)

    //绘制矩形,left,right,top,bottom分别是矩形四条边的坐标
    //canvas.drawRect(float left, float top, float right, float bottom, @NonNull Paint paint)

    //绘制圆角矩形
    //canvas.drawRoundRect(float left, float top, float right, float bottom, float rx, float ry,
    //  @NonNull Paint paint)

    //绘制椭圆
    //canvas.drawOval(float left, float top, float right, float bottom, @NonNull Paint paint)

    //绘制扇形或者弧形
    //canvas.drawArc(float left, float top, float right, float bottom, float startAngle,
    //    float sweepAngle, boolean useCenter, @NonNull Paint paint)

    //绘制自定义图形
    //canvas.drawPaint(path,paint)

    /**
     * Path.FillType.WINDING:（默认）非0环绕原则
     *
     *
     *
     * Path.FillType.EVEN_ODD：奇偶原则
     * 对于平面任意一点向任意方向射出一条射线，这条射线和图形相交的次数如果是奇数则这个点被任务在图形内部，也就是
     * 要被涂色的区域，否则在（偶数）外部不被涂色
     *
     * Path.FillType.INVERSE_WINDING
     * Path.FillType.INVERSE_EVEN_ODD
     *
     */
    path.fillType=Path.FillType.EVEN_ODD

}