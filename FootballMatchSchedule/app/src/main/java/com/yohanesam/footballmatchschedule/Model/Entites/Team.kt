package com.yohanesam.footballmatchschedule.Model.Entites

import com.google.gson.annotations.SerializedName

data class Team(

    @SerializedName("idTeam")
    val idTeam: String? = null,

    @SerializedName("strTeam")
    val strTeamName: String? = null,

    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null

)