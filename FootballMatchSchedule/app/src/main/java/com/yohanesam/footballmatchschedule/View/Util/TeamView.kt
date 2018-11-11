package com.yohanesam.footballmatchschedule.View.Util

import com.yohanesam.footballmatchschedule.Model.Entites.Team

interface TeamView {

    fun isLoad()
    fun stopLoad()
    fun showTeamResult(homeTeamData: List<Team>?, awayTeamData: List<Team>?)

}