package com.yohanesam.footballmatchschedule.View.Util

import com.yohanesam.footballmatchschedule.Model.Entity.Match

interface MatchUtil {
    fun isLoad()
    fun stopLoad()
    fun showResult(data: List<Match>?)
}