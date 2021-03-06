package com.yohanesam.footballmatchschedule.view.util

import com.yohanesam.footballmatchschedule.model.entites.Team

interface TeamView {

    fun isLoad()
    fun stopLoad()
    fun showTeamResult(homeTeamData: List<Team>?, awayTeamData: List<Team>?)

}