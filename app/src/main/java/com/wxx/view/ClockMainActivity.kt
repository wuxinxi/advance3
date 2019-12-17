package com.wxx.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.clock_view.*
import java.util.*

class ClockMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_view)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR + 1)
        val min = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        clockView.setCurrentTime(hour, min, second)



    }

    override fun onDestroy() {
        super.onDestroy()
        clockView.stop()
    }
}
