package com.yohanesam.footballmatchschedule.presenter.coroutines

import android.content.Context
import android.view.View
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.helper.database
import org.jetbrains.anko.db.*
import com.yohanesam.footballmatchschedule.model.Entity.Match
import com.yohanesam.footballmatchschedule.model.responsesdata.MatchJSONArray
import com.yohanesam.footballmatchschedule.presenter.apis.APIRepository
import com.yohanesam.footballmatchschedule.presenter.apis.SportAPI
import com.yohanesam.footballmatchschedule.view.interfaces.MatchView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.design.snackbar
import java.sql.SQLClientInfoException

class MatchDetailCoroutine(
    val view: MatchView,
    val apiRepository: APIRepository,
    val gson: Gson
) {

    fun getSelectedMatch(matchId: String?) {

        view.isLoad()

        async(UI) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(SportAPI.getSelectedMatch(matchId)),
                    MatchJSONArray::class.java
                )
            }

            view.showResult(data.await().arrMatchesResult)
            view.stopLoad()

        }

    }

    fun addToFavorite(view: View, context: Context, data: Match) {
        try {
            context.database.use {
                insert(
                    Match.FAVORITE_MATCH,
                    Match.ID_EVENT to data.id,
                    Match.ID_EVENT to data.idEvent,
                    Match.DATE to data.dateEvent,

                    // Home Team Column
                    Match.HOME_TEAM_ID to data.idHomeTeam,
                    Match.HOME_TEAM_NAME to data.strHomeTeam,
                    Match.HOME_TEAM_SCORE to data.intHomeScore,
                    Match.HOME_TEAM_SHOT to data.intHomeShots,
                    Match.HOME_TEAM_GOAL_DETAIL to data.strHomeGoalDetails,
                    Match.HOME_TEAM_YELLOW_CARDS to data.strHomeYellowCards,
                    Match.HOME_TEAM_RED_CARDS to data.strHomeRedCards,
                    Match.HOME_TEAM_LINEUP_SUBSTITUTE to data.strHomeLineupSubstitutes,

                    // AWAY Team Column
                    Match.AWAY_TEAM_ID to data.idAwayTeam,
                    Match.AWAY_TEAM_NAME to data.strAwayTeam,
                    Match.AWAY_TEAM_SCORE to data.intAwayScore,
                    Match.AWAY_TEAM_SHOT to data.intAwayShots,
                    Match.AWAY_TEAM_GOAL_DETAIL to data.strAwayGoalDetails,
                    Match.AWAY_TEAM_YELLOW_CARDS to data.strAwayYellowCards,
                    Match.AWAY_TEAM_RED_CARDS to data.strAwayRedCards,
                    Match.AWAY_TEAM_LINEUP_SUBSTITUTE to data.strAwayLineupSubstitutes
                )
            }
            snackbar(view, "Added To Favorite").show()
        } catch (e: SQLClientInfoException) {
            snackbar(view, "Error when adding your match. Please try again").show()
        }
    }

    fun removeFromFavorite(view: View, context: Context, data: Match) {
        try {
            context.database.use {
                delete(
                    Match.FAVORITE_MATCH,
                    Match.ID_EVENT + " = {id}",
                    "id" to data.idEvent.toString()
                    )
            }

            snackbar(view, "Remove From Favorite").show()
        } catch (e: SQLClientInfoException) {
            snackbar(view, "Error when removing your match. Please try again").show()
        }
    }

    fun isFavorite(context: Context, data: Match): Boolean {

        var boolFavorite = false

        context.database.use {

            val favorites =select(Match.FAVORITE_MATCH)
                .whereArgs(Match.ID_EVENT + " = {id} ",
                "id" to data.idEvent.toString())
                .parseList(classParser<Match>())

            boolFavorite = !favorites.isEmpty()

        }

        return boolFavorite
    }

}