package com.wxx.view.advance.view.clockdial

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.wxx.view.getCurrentDate

/**
 * @author ：wuxinxi on 2019/12/11 .
 * @packages ：com.wxx.view.advance.clockdial .
 * TODO:芝麻信用
 */
class AliSesameCreditView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    val paintOut = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintOut2 = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintIn = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintBigCalibration = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintSmallCalibration = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val itemTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val tipTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val tipTextPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    val rectFOut = RectF()
    val rectFOut2 = RectF()
    val rectFIn = RectF()
    var width = 0f
    var height = 0f

    var space = 50f

    var centerX = 0f

    val paintInStroke = 50f

    val textSize = 40f
    val itemTextSize = 35f
    val markTextSize = 200f
    val tipTextSize = 100f
    val tipTextSize2 = 50f

    private val number = arrayOf(350, 400, 450, 500, 550, 600, 650)
    private val item = arrayOf("较差", "中等", "良好", "优秀", "较好", "极好")

    val markPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mark = 350
    var realMark = 0
        set(value) {
            field = value
            if (realMark < 350) {
                field = 350
            }
            val progress = (realMark - 350) / 300f * 100
            startAnimator(progress)
        }

    private var tip = "较差"

    private var tip2 = "评估时间：2019-12-11"

    init {
        val displayMetrics = context.resources.displayMetrics
        width = displayMetrics.widthPixels.toFloat()
        height = width

        paintOut.color = Color.GRAY
        paintOut.style = Paint.Style.STROKE
        paintOut.strokeWidth = 10f

        paintOut2.color = Color.WHITE
        paintOut2.style = Paint.Style.STROKE
        paintOut2.strokeCap = Paint.Cap.ROUND
        paintOut2.strokeWidth = 15f

        paintIn.color = Color.parseColor("#F1F3F4")
        paintIn.style = Paint.Style.STROKE
        paintIn.strokeWidth = paintInStroke

        paintBigCalibration.color = Color.BLACK
        paintBigCalibration.style = Paint.Style.STROKE
        paintBigCalibration.strokeWidth = 10f

        paintSmallCalibration.color = Color.BLACK
        paintSmallCalibration.style = Paint.Style.STROKE
        paintSmallCalibration.strokeWidth = 5f

        centerX = width / 2

        rectFOut.set(space, space, width - space, height - space)
        rectFOut2.set(space, space, width - space, height - space)
        space += 70f
        rectFIn.set(space, space, width - space, height - space)

        textPaint.color = Color.BLACK
        textPaint.textSize = textSize

        itemTextPaint.color = Color.WHITE
        itemTextPaint.textSize = itemTextSize

        markPaint.color = Color.WHITE
        markPaint.textSize = markTextSize

        tipTextPaint.color = Color.RED
        tipTextPaint.textSize = tipTextSize

        tipTextPaint2.color = Color.GREEN
        tipTextPaint2.textSize = tipTextSize2
        tip2 = "评估时间：${getCurrentDate()}"

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(rectFOut, 135f, 270f, false, paintOut)
        canvas.drawArc(rectFIn, 135f, 270f, false, paintIn)
        drawBigScale(canvas)
        drawInText(canvas)
        drawArc(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var specMode = MeasureSpec.getMode(widthMeasureSpec)
        var specSize = MeasureSpec.getSize(widthMeasureSpec)
        when (specMode) {
            MeasureSpec.AT_MOST -> paddingLeft + paddingRight + specSize.toFloat()
            MeasureSpec.EXACTLY -> width += paddingLeft + paddingRight
            else -> {
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec)
        specSize = MeasureSpec.getSize(heightMeasureSpec)
        when (specMode) {
            MeasureSpec.AT_MOST -> paddingTop + paddingBottom + specSize.toFloat()
            MeasureSpec.EXACTLY -> height += paddingTop + paddingBottom
            else -> {
            }
        }

        setMeasuredDimension(width.toInt(), height.toInt())
    }

    /**
     * 绘制大刻度
     */
    private fun drawBigScale(canvas: Canvas) {
        canvas.save()
        //逆时针旋转到圆点的顶部位置，旋转轴为圆心
        canvas.rotate(-135f, width / 2f, width / 2f)
        val angle = 270 / 6f
        for (i in 1..7) {
            canvas.drawLine(
                width / 2,
                space - paintInStroke / 2,
                width / 2,
                space - paintInStroke / 2 + paintInStroke,
                paintBigCalibration
            )
            //文字宽度
            val textWidth = textPaint.measureText(number[i - 1].toString())
            canvas.drawText(
                number[i - 1].toString(),
                width / 2 - textWidth / 2,
                space - paintInStroke / 2 + paintInStroke + textSize,
                textPaint
            )
            canvas.rotate(angle, width / 2f, width / 2f)
        }
        canvas.restore()
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        canvas.save()
        //逆时针旋转到圆点的顶部位置，旋转轴为圆心
        //item文字在两个刻度中间，因此需要在加上刻度角度的一半
        canvas.rotate(-135f + 270 / 6 / 2, width / 2f, width / 2f)
        val angle = 270 / 6f
        for (i in 1..7) {
            if (i != 7) {
                val itemTextWidth = itemTextPaint.measureText(item[i - 1])
                canvas.drawText(
                    item[i - 1],
                    width / 2 - itemTextWidth / 2,
                    space - paintInStroke / 2 + paintInStroke + textSize,
                    itemTextPaint
                )
            }
            canvas.rotate(angle, width / 2f, width / 2f)
        }
        canvas.restore()
    }

    /**
     * 绘制内部文字
     */
    private fun drawInText(canvas: Canvas) {
        var textWidth = markPaint.measureText(mark.toString())
        canvas.drawText(mark.toString(), width / 2 - textWidth / 2, width / 2, markPaint)

        var fontMetrics = markPaint.fontMetrics
        //获取文字高度
        var textHeight = fontMetrics.bottom - fontMetrics.top
        val relTip = "信用$tip"
        textWidth = tipTextPaint.measureText(relTip)
        canvas.drawText(relTip, width / 2 - textWidth / 2, width / 2 + textHeight / 2, tipTextPaint)

        fontMetrics = tipTextPaint.fontMetrics
        textHeight += fontMetrics.bottom - fontMetrics.top

        textWidth = tipTextPaint2.measureText(tip2)
        canvas.drawText(tip2, width / 2 - textWidth / 2, width / 2 + textHeight / 2, tipTextPaint2)
    }

    var progress = 0f
        set(value) {
            field = value
            val temp = field
            field *= 270f / 100
            mark = if (field.toInt() == 0) {
                350
            } else {
                Math.ceil((350 + (300 / 100) * temp).toDouble()).toInt()
            }
            tip = getTip(mark)
            invalidate()
        }


    /**
     * 绘制外弧线
     */
    private fun drawArc(canvas: Canvas) {
        canvas.drawArc(rectFOut2, 135f, progress, false, paintOut2)
    }

    private fun startAnimator(progress: Float) {
        val animator = ValueAnimator.ofFloat(0f, progress)
        animator.duration = 2000
        animator.addUpdateListener {
            this.progress = it.animatedValue as Float
        }
        animator.start()
    }

    private fun getTip(mark: Int): String {
        tipTextPaint.color = Color.WHITE
        return when (mark) {
            in 350..400 -> {
                tipTextPaint.color = Color.RED
                item[0]
            }
            in 401..450 -> item[1]
            in 451..500 -> item[2]
            in 501..550 -> item[3]
            in 551..600 -> item[4]
            in 601..650 -> item[5]
            else -> item[5]
        }
    }
}