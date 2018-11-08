package com.yohanesam.footballmatchschedule.Model.TheResponces

import com.google.gson.annotations.SerializedName
import com.yohanesam.footballmatchschedule.Model.Entity.Match

data class MatchResponse (
    @SerializedName("events")
    val events: List<Match>? = null,

    @SerializedName("event")
    val event: List<Match>? = null
)