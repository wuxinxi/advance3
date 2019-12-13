package com.wxx.view

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*



/**
 * @author ：wuxinxi on 2019/12/10 .
 * @packages ：com.wxx.view .
 * TODO:一句话描述
 */


fun dp2Pixel(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics;
    return dp * metrics.density
}

fun getCurrentDate(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    return simpleDateFormat.format(Date())
}

/**
 * @param context 上下文
 * @return 屏幕尺寸信息
 */
fun getMetrics(context: Context): DisplayMetrics {
    val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    manager.defaultDisplay.getMetrics(metrics)
    return metrics
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}