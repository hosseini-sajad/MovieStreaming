package com.moviestreaming.utils.mapper

import android.app.Activity
import androidx.viewpager.widget.ViewPager
import java.util.*

object SliderPageUtil{
    private var timer: Timer? = null

    fun sliderAutoChange(activity: Activity, length: Int, viewPager: ViewPager) {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    if (viewPager.currentItem < length - 1)
                        viewPager.currentItem = viewPager.currentItem + 1
                    else
                        viewPager.currentItem = 0
                }
            }
        }, 2000, 5000)
    }
}