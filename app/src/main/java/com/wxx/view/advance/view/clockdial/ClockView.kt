package com.wxx.view.advance.view.clockdial

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import com.wxx.view.R

/**
 * @author ：wuxinxi on 2019/12/12 .
 * @packages ：com.wxx.view.advance.clockdial .
 * TODO:时钟
 * 1. 外圆
 * 2. 大刻度
 * 3. 小刻度
 * 4. 时针
 * 5. 分针
 * 6. 秒针
 */
class ClockView : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet!!, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        init(context, attributeSet)
    }

    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bigScalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val smallScalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val hourPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val minPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val secondPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var circleColor = Color.RED
    var scaleColor = Color.BLACK
    var hourColor = Color.GREEN
    var minColor = Color.YELLOW
    var secondColor = Color.BLUE
    var textColor = Color.BLACK

    val stroke = 10f
    val bigScaleStroke = 7f
    val bigScaleLen = 40f
    val smallScaleStroke = 4f
    val smallScaleLen = 20f
    val hourStroke = 25f
    val minStroke = 15f
    val secondStroke = 8f

    val textSize = 50f

    var phoneWidth = 0
    var viewHeight = 0
    var radius = 0f
    var space = 100
    var centerX = 0f
    var centerY = 0f

    //背景是否全充满
    var bgFill = false

    private val itemHour = Array(12) { i -> i + 1 }

    private fun init(context: Context, attributeSet: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ClockView)
        circlePaint.color = typeArray.getColor(R.styleable.ClockView_clock_ring_color, circleColor)
        circlePaint.style = if (typeArray.getBoolean(
                R.styleable.ClockView_clock_bg_fill,
                bgFill
            )
        ) Paint.Style.FILL else Paint.Style.STROKE
        bigScalePaint.color = typeArray.getColor(R.styleable.ClockView_clock_big_scale_color, scaleColor)
        smallScalePaint.color = typeArray.getColor(R.styleable.ClockView_clock_small_scale_color, scaleColor)
        hourPaint.color = typeArray.getColor(R.styleable.ClockView_clock_hour_color, hourColor)
        minPaint.color = typeArray.getColor(R.styleable.ClockView_clock_min_color, minColor)
        secondPaint.color = typeArray.getColor(R.styleable.ClockView_clock_second_color, secondColor)
        textPaint.color = typeArray.getColor(R.styleable.ClockView_clock_text_color, textColor)

        typeArray.recycle()

        circlePaint.strokeWidth = stroke

        bigScalePaint.style = Paint.Style.STROKE
        bigScalePaint.strokeWidth = bigScaleStroke

        smallScalePaint.style = Paint.Style.STROKE
        smallScalePaint.strokeWidth = smallScaleStroke

        hourPaint.style = Paint.Style.STROKE
        hourPaint.strokeWidth = hourStroke
        hourPaint.strokeCap = Paint.Cap.ROUND

        minPaint.style = Paint.Style.STROKE
        minPaint.strokeWidth = minStroke
        minPaint.strokeCap = Paint.Cap.ROUND

        secondPaint.style = Paint.Style.STROKE
        secondPaint.strokeWidth = secondStroke
        secondPaint.strokeCap = Paint.Cap.ROUND

        textPaint.textSize = textSize


        val displayMetrics = context.resources.displayMetrics
        phoneWidth = displayMetrics.widthPixels

        radius = phoneWidth / 2f - space
        centerX = phoneWidth / 2f
        centerY = radius + space

        viewHeight =(centerY*2).toInt()

        startAnimator()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawScale(canvas)
        drawHour(canvas)
        drawMin(canvas)
        drwSecond(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(phoneWidth, viewHeight)

    }

    /**
     * 绘制表环
     */
    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius, circlePaint)
    }

    /**
     * 绘制刻度
     */
    private fun drawScale(canvas: Canvas) {
        canvas.save()
        canvas.rotate(360 / 12f, centerX, centerY)
        val angle = 360 / 60f
        for (i in 0 until 60) {
            if (i % 5 == 0) {
                canvas.drawLine(
                    phoneWidth / 2f,
                    //间隔的位置坐标减去stroke的一半+圆的stroke（因为会超出）
                    space - bigScaleStroke / 2 + stroke,
                    phoneWidth / 2f,
                    space + bigScaleLen,
                    bigScalePaint
                )
                val textWidth = textPaint.measureText(itemHour[i / 5].toString())
                canvas.drawText(
                    itemHour[i / 5].toString(),
                    centerX - textWidth / 2,
                    space + bigScaleLen + bigScaleStroke + textSize,
                    textPaint
                )
            } else {
                canvas.drawLine(
                    phoneWidth / 2f,
                    space - smallScaleStroke / 2 + stroke,
                    phoneWidth / 2f,
                    space + smallScaleLen,
                    smallScalePaint
                )
            }
            canvas.rotate(angle, centerX, centerY)
        }
        canvas.restore()
    }

    /**
     * 绘制秒针
     */
    private fun drwSecond(canvas: Canvas) {
        canvas.save()
        canvas.rotate(secondDegrees, centerX, centerY)
        canvas.drawLine(centerX, centerY, centerX, radius - 200, secondPaint)
        canvas.restore()
    }

    /**
     * 绘制分针
     */
    private fun drawMin(canvas: Canvas) {
        canvas.save()
        canvas.rotate(minDegrees, centerX, centerY)
        canvas.drawLine(centerX, centerY, centerX, radius - 180, minPaint)
        canvas.restore()
    }

    /**
     * 绘制时针
     */
    private fun drawHour(canvas: Canvas) {
        canvas.save()
        canvas.rotate(hourDegrees, centerX, centerY)
        canvas.drawLine(centerX, centerY, radius - 100, centerY, hourPaint)
        canvas.restore()
    }

    var secondDegrees = 0f
        set(value) {
            field = value
            invalidate()
        }

    var minDegrees = 0f

    //时区使用北京时区，默认8点，时针偏转到-30°
    var hourDegrees = -30f
    lateinit var animatorHandler: Handler
    private fun startAnimator() {
        animatorHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                //秒针
                //大于等于360说明过了一分钟
                if (secondDegrees >= 360) {
                    secondDegrees = 360 / 60f
                    //1分钟 时针走了0.5°
                    if (hourDegrees >= 360) {
                        hourDegrees = 0f
                    } else {
                        hourDegrees += 0.5f
                    }
                } else {
                    secondDegrees += 360 / 60f
                }
                //分钟:秒针转过1圈
                if (minDegrees >= 360) {
                    minDegrees = 360 / 60 / 60f
                } else {
                    minDegrees += 360 / 60 / 60f
                }

                animatorHandler.postDelayed(this, 1000)
            }
        }
        animatorHandler.post(runnable)
    }

    /**
     * 设置当前时间
     */
    fun setCurrentTime(hour: Int, min: Int, second: Int) {
        var currentHour = hour
        if (currentHour > 12) {
            currentHour -= 12
        }
        hourDegrees = (9 - hour) * (-30).toFloat()
        minDegrees = min * 6f
        secondDegrees = second * 6f

        val secondAnimator = ValueAnimator.ofFloat(0f, secondDegrees)
        secondAnimator.addUpdateListener {
            secondDegrees = it.animatedValue as Float
        }
        val minAnimator = ValueAnimator.ofFloat(0f, minDegrees)
        minAnimator.addUpdateListener {
            minDegrees = it.animatedValue as Float
        }
        val hourAnimator = ValueAnimator.ofFloat(-30f, hourDegrees)
        hourAnimator.addUpdateListener {
            hourDegrees = it.animatedValue as Float
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(secondAnimator, minAnimator, hourAnimator)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    fun stop() {
        animatorHandler.removeCallbacksAndMessages(null)
    }

}