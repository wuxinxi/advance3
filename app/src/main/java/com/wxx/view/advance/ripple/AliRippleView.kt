package com.wxx.view.advance.ripple

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wxx.view.R

/**
 * @author ：wuxinxi on 2019/12/11 .
 * @packages ：com.wxx.view.advance.ripple .
 * TODO:咻一咻
 */
class AliRippleView : View {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val ripplePaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    val initColor = Color.BLUE
    var color = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }
    var color2 = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    lateinit var bitmap: Bitmap
    val path = Path()

    var centerX = 0f
    var centerY = 0f
    var radius = 0f
    var initRippleRadius = 0f
    var rippleRadius = 0f
        set(value) {
            field = value
            invalidate()
        }

    var rippleRadius2 = 0f
        set(value) {
            field = value
            invalidate()
        }


    var alpha = 255
        set(value) {
            field = alpha
            invalidate()
        }

    var width = 0f

    val params = mutableListOf<Params>()

    init {
        val displayMetrics = context.resources.displayMetrics
        width = displayMetrics.widthPixels.toFloat()
        centerX = width / 2f
        centerY = centerX
        radius = width / 10f

        bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.head)
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW)
        ripplePaint.color = color
        ripplePaint2.color = color
        rippleRadius = radius
        initRippleRadius = rippleRadius

        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))
        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))
        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))
        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))
        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))
        params.add(Params(radius, Paint(Paint.ANTI_ALIAS_FLAG)))

        startAnimator()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (param in params) {
            canvas.drawCircle(centerX, centerY, param.radius, param.paint)
        }

        canvas.save()
        canvas.clipPath(path)
        //先中心缩放再裁剪
        canvas.scale(0.2f,0.2f,centerX,centerY)
        canvas.drawBitmap(bitmap, centerX - bitmap.width / 2, centerY - bitmap.height / 2, paint)
        canvas.restore()

    }


    fun startAnimator() {
        var delay = 200L
        val animatorSet = AnimatorSet()
        val animators = mutableListOf<Animator>()
        for (param in params) {
            val argbAnimator = ValueAnimator.ofArgb(initColor, Color.WHITE)
            argbAnimator.repeatMode = ObjectAnimator.RESTART
            argbAnimator.repeatCount = ObjectAnimator.INFINITE
            argbAnimator.startDelay = delay
            argbAnimator.addUpdateListener {
                param.paint.color = it.animatedValue as Int
                invalidate()
            }
            animators.add(argbAnimator)
            val radiusAnimator = ValueAnimator.ofFloat(initRippleRadius, width / 2)
            radiusAnimator.startDelay = delay
            radiusAnimator.repeatMode = ObjectAnimator.RESTART
            radiusAnimator.repeatCount = ObjectAnimator.INFINITE
            radiusAnimator.addUpdateListener {
                param.radius = it.animatedValue as Float
                invalidate()
            }
            animators.add(radiusAnimator)
            delay += 200
        }
        animatorSet.duration =3000
        animatorSet.playTogether(animators)
        animatorSet.start()

    }

    class Params(var radius: Float, var paint: Paint = Paint())
}