package com.yohanesam.footballmatchschedule.Model.TheResponces

import com.google.gson.annotations.SerializedName
import com.yohanesam.footballmatchschedule.Model.Entites.Team

data class TeamJSONArray(

    @SerializedName("teams")
    val jsonArrayTeamResult: List<Team>? = null

)