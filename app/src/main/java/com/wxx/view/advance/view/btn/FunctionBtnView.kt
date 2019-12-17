package com.wxx.view.advance.view.btn

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wxx.view.dp2Pixel

/**
 * @author ：wuxinxi on 2019/12/10 .
 * @packages ：com.wxx.view.advance.btn .
 * TODO:弹出功能按钮
 */
class FunctionBtnView : View {
    /**
     * 圆 paint
     */
    private val circlePint = Paint()

    /**
     * round矩形 paint
     */
    private val roundRectPaint = Paint()

    /**
     * text paint
     */
    private val textPaint = Paint()

    private val itemPaint = Paint()

    var itemAlpha = 0
        set(value) {
            field = value
            invalidate()
        }

    /**
     *  圆 color
     */
    private val circleColor = Color.parseColor("#FE8922")

    /**
     *  round矩形  color
     */
    private val roundRectColor = Color.parseColor("#FDCE36")

    private val bigPaint = Paint()

    val circleRadius = 100f

    var centerX = 0f
    var centerY = 0f
    var radius = 0f

    var viewWidth = 0f
    var height = 0f
    var width = 0f

    var rectleft = 0f
        set(value) {
            field = value
            invalidate()
        }

    var text1 = "声音"
        set(value) {
            field = value
            invalidate()
        }
    var text2 = "视频"
        set(value) {
            field = value
            invalidate()
        }

    var text3 = "照片"
        set(value) {
            field = value
            invalidate()
        }

    var text4 = "文字"
        set(value) {
            field = value
            invalidate()
        }

    var roundRectF: RectF

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    var bigCenterX = 0f
    var bigCenterY = 0f
    var bigRadius = 0f
        set(value) {
            field = value
            invalidate()
        }

    var roundRectHeight = 0f

    var status = false

    /**
     * 大圆比内圆大
     */
    private val bigToCircleDistance = 10

    init {
        circlePint.isAntiAlias = true
        circlePint.color = circleColor
        circlePint.style = Paint.Style.FILL

        textPaint.color = Color.WHITE
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = dp2Pixel(20f)

        itemPaint.color = Color.WHITE
        itemPaint.textAlign = Paint.Align.CENTER
        itemPaint.textSize = dp2Pixel(20f)

        val metrics = context.resources.displayMetrics
        viewWidth = metrics.widthPixels.toFloat()

        roundRectPaint.color = roundRectColor
        roundRectPaint.style = Paint.Style.FILL

        bigPaint.style = Paint.Style.FILL
        bigPaint.color = roundRectColor

        roundRectHeight = circleRadius * 2 + 50
        roundRectF = RectF()
        height = roundRectHeight + 50
        width = viewWidth

        bigCenterX = viewWidth - 50 - circleRadius + bigToCircleDistance
        bigCenterY = 100 + circleRadius / 2
        bigRadius = circleRadius + 20


        centerX = bigCenterX
        centerY = bigCenterY
        radius = bigRadius - 20
        rectleft = viewWidth - bigRadius * 2

        left = viewWidth - radius * 8 - radius
        startBreathing()

    }

    var left = 0f

    private fun click() {
        if (status) {
            val animator = ValueAnimator.ofFloat(left, viewWidth - bigRadius * 2)
            animator.addUpdateListener {
                rectleft = (it.animatedValue as Float)
            }

            val alphaAnimator = ValueAnimator.ofInt(255, 0)
            alphaAnimator.addUpdateListener {
                itemAlpha = (it.animatedValue as Int)
            }
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animator, alphaAnimator)
            animatorSet.duration = 300
            animatorSet.start()
        } else {
            val animator = ValueAnimator.ofFloat(viewWidth - bigRadius * 2, left)
            animator.addUpdateListener {
                rectleft = (it.animatedValue as Float)
            }

            val alphaAnimator = ValueAnimator.ofInt(0, 255)
            alphaAnimator.addUpdateListener {
                itemAlpha = (it.animatedValue as Int)
            }
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animator, alphaAnimator)
            animatorSet.duration = 300
            animatorSet.start()
        }
        status = !status
    }

    lateinit var animator: ValueAnimator
    private fun startBreathing() {
        animator = ValueAnimator.ofFloat(bigRadius, bigRadius - 5)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.addUpdateListener {
            bigRadius = it.animatedValue as Float
        }
        animator.duration = 1000
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        roundRectF.set(rectleft, 50f, viewWidth - 30f, roundRectHeight)
        canvas.drawRoundRect(roundRectF, circleRadius, circleRadius, roundRectPaint)
        canvas.drawCircle(bigCenterX, bigCenterY, bigRadius, bigPaint)
        canvas.drawCircle(centerX, centerY, radius, circlePint)
        canvas.drawText(text1, centerX, centerY - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)

        itemPaint.alpha = itemAlpha
        canvas.drawText(
            text2,
            centerX - radius * 2,
            centerY - (itemPaint.descent() + itemPaint.ascent()) / 2,
            itemPaint
        )
        canvas.drawText(
            text3,
            centerX - radius * 4,
            centerY - (itemPaint.descent() + itemPaint.ascent()) / 2,
            itemPaint
        )
        canvas.drawText(
            text4,
            centerX - radius * 6,
            centerY - (itemPaint.descent() + itemPaint.ascent()) / 2,
            itemPaint
        )
    }

    var lastX = 0
    var lastY = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {
                val left4 = viewWidth - bigRadius * 2 - 30f
                val top4 = 50f
                val right4 = viewWidth - 30f
                val bottom4 = 50 + bigRadius * 2f

                val left1 = left
                val top1 = 50f - bigToCircleDistance / 2
                val right1 = left1 + radius * 2 + 30f
                val bottom1 = 50 + radius * 2f - bigToCircleDistance / 2

                val left2 = right1
                val top2 = 50f - bigToCircleDistance / 2
                val right2 = left2 + radius * 2 + 30f
                val bottom2 = 50 + radius * 2f - bigToCircleDistance / 2

                val left3 = right2
                val top3 = 50f - bigToCircleDistance / 2
                val right3 = left3 + radius * 2 + 30f
                val bottom3 = 50 + radius * 2f - bigToCircleDistance / 2

                //按下的范围
                //最左边
                if (lastX > left4 && lastX < right4 && lastY > top4 && lastY < bottom4) {
                    //抬起时的范围
                    if (x > left4 && x < right4 && y > top4 && y < bottom4) {
                        click()
                    }
                } else if (lastX > left1 && lastX < right1 && lastY > top1 && lastY < bottom1) {
                    //第一个
                    if (x > left1 && x < right1 && y > top1 && y < bottom1) {
                        val temp = text1
                        text1 = text4
                        text4 = temp
                        click()

                    }
                } else if (lastX > left2 && lastX < right2 && lastY > top2 && lastY < bottom2) {
                    //第二个
                    if (x > left2 && x < right2 && y > top2 && y < bottom2) {
                        val temp = text1
                        text1 = text3
                        text3 = temp
                        click()

                    }
                } else if (lastX > left3 && lastX < right3 && lastY > top3 && lastY < bottom3) {
                    //第三个
                    if (x > left3 && x < right3 && y > top3 && y < bottom3) {
                        val temp = text1
                        text1 = text2
                        text2 = temp
                        click()

                    }
                }
            }
        }
        return true
    }

    fun stop() {
        animator.let {
            animator.removeAllUpdateListeners()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var specMode = MeasureSpec.getMode(widthMeasureSpec)
        var specSize = MeasureSpec.getSize(widthMeasureSpec)
        when (specMode) {
            //AT_MOST->wrap_content
            MeasureSpec.AT_MOST -> width = (paddingLeft + paddingRight + specSize).toFloat()
            //EXACTLY->精确大小或者match_parent
            MeasureSpec.EXACTLY -> width += paddingLeft + paddingRight
            else -> {
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec)
        specSize = MeasureSpec.getSize(heightMeasureSpec)
        when (specMode) {
            MeasureSpec.AT_MOST -> height = (paddingTop + paddingBottom + specSize).toFloat()
            MeasureSpec.EXACTLY -> height += paddingBottom + paddingTop
            else -> {
            }
        }
        setMeasuredDimension(width.toInt(), height.toInt())
    }

}