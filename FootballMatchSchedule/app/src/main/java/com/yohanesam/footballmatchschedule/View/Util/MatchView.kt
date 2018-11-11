package com.yohanesam.footballmatchschedule.View.Util

import com.yohanesam.footballmatchschedule.Model.Entity.Match

interface MatchView {

    fun isLoad()
    fun stopLoad()
    fun showResult(data: List<Match>?)

}