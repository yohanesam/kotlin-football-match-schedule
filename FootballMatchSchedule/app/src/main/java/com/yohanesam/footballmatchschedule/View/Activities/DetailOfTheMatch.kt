package com.yohanesam.footballmatchschedule.View.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.Entites.Team
import com.yohanesam.footballmatchschedule.Model.Entity.Match
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.Coroutines.MatchDetailCoroutine
import com.yohanesam.footballmatchschedule.Presenter.Coroutines.TeamDetailCoroutine
import com.yohanesam.footballmatchschedule.R
import com.yohanesam.footballmatchschedule.View.Util.MatchView
import com.yohanesam.footballmatchschedule.View.Util.TeamView
import kotlinx.android.synthetic.main.activity_detail_of_the_match.*

class DetailOfTheMatch : AppCompatActivity(), MatchView, TeamView {

    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String
    private lateinit var idMatch: String

    private lateinit var homeTeamDataList: Team
    private lateinit var awayTeamDataList: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_of_the_match)

        idMatch = intent.getStringExtra("ID_MATCH")
        idHomeTeam = intent.getStringExtra("ID_HOME_TEAM")
        idAwayTeam = intent.getStringExtra("ID_AWAY_TEAM")

        val req = APIRepository()
        val gson = Gson()

        val matchDetailCoroutine = MatchDetailCoroutine(this, req, gson)
        val teamDetailCoroutine = TeamDetailCoroutine(this, req, gson)

        matchDetailCoroutine.getSelectedMatch(idMatch)
        teamDetailCoroutine.getSelectedTeam(idHomeTeam, idAwayTeam)

    }

    override fun isLoad() {
        pbProgressDetailActivity.visibility = View.VISIBLE
    }

    override fun stopLoad() {
        pbProgressDetailActivity.visibility = View.GONE
    }

    override fun showResult(data: List<Match>?) {
        val listOfMatchData = Match(
            data?.get(0)?.idEvent ?: "-",
            data?.get(0)?.idHomeTeam ?: "-",
            data?.get(0)?.idAwayTeam ?: "-",
            data?.get(0)?.strHomeTeam ?: "-",
            data?.get(0)?.strAwayTeam ?: "-",
            data?.get(0)?.intHomeScore ?: "-",
            data?.get(0)?.intAwayScore ?: "-",
            data?.get(0)?.dateEvent ?: "-",
            data?.get(0)?.intHomeShots ?: "-",
            data?.get(0)?.intAwayShots ?: "-",
            data?.get(0)?.strHomeGoalDetails ?: "-",
            data?.get(0)?.strAwayGoalDetails ?: "-",
            data?.get(0)?.strHomeYellowCards ?: "-",
            data?.get(0)?.strAwayYellowCards ?: "-",
            data?.get(0)?.strHomeRedCards ?: "-",
            data?.get(0)?.strAwayRedCards ?: "-",
            data?.get(0)?.strHomeLineupSubstitutes ?: "-",
            data?.get(0)?.strAwayLineupSubstitutes ?: "-"
        )

        tvHomeTeamName.text = listOfMatchData.strHomeTeam
        tvHomeTeamScore.text = listOfMatchData.intHomeScore
        tvHomeTeamShots.text = listOfMatchData.intHomeShots
        tvHomeGoalMakers.text = listOfMatchData.strHomeGoalDetails
        tvHomeTeamYellowCards.text = listOfMatchData.strHomeYellowCards
        tvHomeTeamRedCards.text = listOfMatchData.strHomeRedCards
        tvHomeTeamSubtitutePlayer.text = listOfMatchData.strHomeLineupSubstitutes

        tvAwayTeamName.text = listOfMatchData.strAwayTeam
        tvAwayTeamScore.text = listOfMatchData.intAwayScore
        tvAwayTeamShots.text = listOfMatchData.intAwayShots
        tvAwayGoalMakers.text = listOfMatchData.strAwayGoalDetails
        tvAwayTeamYellowCards.text = listOfMatchData.strAwayYellowCards
        tvAwayTeamRedCards.text = listOfMatchData.strAwayRedCards
        tvAwayTeamSubtitutePlayer.text = listOfMatchData.strAwayLineupSubstitutes

        tvMatchDate.text = listOfMatchData.dateEvent

        Log.d("GETDETAIL", listOfMatchData.strAwayRedCards.toString())

    }

    override fun showTeamResult(homeTeamData: List<Team>?, awayTeamData: List<Team>?) {

        homeTeamDataList = Team(
            homeTeamData?.get(0)?.idTeam,
            homeTeamData?.get(0)?.strTeamName,
            homeTeamData?.get(0)?.strTeamBadge
        )
        awayTeamDataList = Team(
            awayTeamData?.get(0)?.idTeam,
            awayTeamData?.get(0)?.strTeamName,
            awayTeamData?.get(0)?.strTeamBadge
        )

        Glide.with(this).load(homeTeamDataList.strTeamBadge).into(ivHomeTeamBadge)
        Glide.with(this).load(awayTeamDataList.strTeamBadge).into(ivAwayTeamBadge)

    }
}
