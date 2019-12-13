package com.wxx.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @author ：wuxinxi on 2019/11/22 .
 * @packages ：com.wxx.view.custom .
 * TODO:
 * layout():设置显示的位置
 * offsetLeftAndRight():设置左右的位置
 * offsetTopAndBottom():设置上下的位置
 * LayoutParams:改变布局参数
 *
 * 点击事件传递的过程：Activity->PhoneWindow->DecorView->ViewGroup->ViewGroup->View
 * 上到下传递：onInterceptTouchEvent()返回false表示不拦截，继续往下传递，true表示拦截自己处理不在传递
 * 下到上传递：事件传递到最底层的子View还未处理，onTouchEvent（）返回false，则传递给父view,以此类推知道onTouchEvent返true被处理
 */
class CustomMoveView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

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
                val offsetX = x - lastX
                val offsetY = y - lastY

                //1. 通过layout进行移动
                layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)

                //2. 通过修改layoutParams进行移动
//                val params = layoutParams as LinearLayout.LayoutParams
//                params.leftMargin = left + offsetX
//                params.rightMargin = right + offsetX
//                params.bottomMargin = bottom + offsetY
//                params.topMargin = top + offsetY
//                layoutParams = params

                //3. 通过scrollBy进行移动
                //偏移信息一定要取反，
//                val view=parent as View
//                view.scrollBy(-offsetX,-offsetY)
            }
        }
        return true
    }
}