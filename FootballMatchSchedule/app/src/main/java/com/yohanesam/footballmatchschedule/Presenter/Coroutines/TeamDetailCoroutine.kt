package com.yohanesam.footballmatchschedule.Presenter.Coroutines

import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.TheResponces.TeamJSONArray
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.APIs.SportAPI
import com.yohanesam.footballmatchschedule.View.Util.TeamView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailCoroutine(
    private val view: TeamView,
    private val apiRepository: APIRepository,
    private val gson: Gson
) {

    fun getSelectedTeam(idHomeTeam: String, idAwayTeam: String) {

        view.isLoad()

        async(UI) {
            val homeTeamData = bg {
                gson.fromJson(
                    apiRepository.doRequest(SportAPI.getSelectedTeam(idHomeTeam)),
                    TeamJSONArray::class.java
                )
            }

            val awayTeamData = bg {
                gson.fromJson(
                    apiRepository.doRequest(SportAPI.getSelectedTeam(idAwayTeam)),
                    TeamJSONArray::class.java
                )
            }

            view.showTeamResult(homeTeamData.await().jsonArrayTeamResult, awayTeamData.await().jsonArrayTeamResult)
            view.stopLoad()
        }

    }

}