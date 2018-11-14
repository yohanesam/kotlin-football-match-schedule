package com.yohanesam.footballmatchschedule.view.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.model.entites.Team
import com.yohanesam.footballmatchschedule.model.Entity.Match
import com.yohanesam.footballmatchschedule.presenter.apis.APIRepository
import com.yohanesam.footballmatchschedule.presenter.coroutines.MatchDetailCoroutine
import com.yohanesam.footballmatchschedule.presenter.coroutines.TeamDetailCoroutine
import com.yohanesam.footballmatchschedule.R
import com.yohanesam.footballmatchschedule.view.interfaces.MatchView
import com.yohanesam.footballmatchschedule.view.interfaces.TeamView
import kotlinx.android.synthetic.main.activity_detail_of_the_match.*



class DetailOfTheMatch : AppCompatActivity(), MatchView, TeamView {

    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String
    private lateinit var idMatch: String

    private lateinit var matchDetailCoroutine: MatchDetailCoroutine
    private lateinit var teamDetailCoroutine: TeamDetailCoroutine

    private lateinit var homeTeamDataList: Team
    private lateinit var awayTeamDataList: Team
    private lateinit var data: Match

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_of_the_match)

        idMatch = intent.getStringExtra("ID_MATCH")
        idHomeTeam = intent.getStringExtra("ID_HOME_TEAM")
        idAwayTeam = intent.getStringExtra("ID_AWAY_TEAM")
        data = intent.getParcelableExtra("EVENT")

        val req = APIRepository()
        val gson = Gson()

        matchDetailCoroutine = MatchDetailCoroutine(this, req, gson)
        teamDetailCoroutine = TeamDetailCoroutine(this, req, gson)

        matchDetailCoroutine.getSelectedMatch(idMatch)
        teamDetailCoroutine.getSelectedTeam(idHomeTeam, idAwayTeam)

        isFavorite = matchDetailCoroutine.isFavorite(this, data)
        Log.d("ISFAVORITE", isFavorite.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.addToFavorite -> {
//                if (isFavorite) {
//                    matchDetailCoroutine.addToFavorite(pbProgressDetailActivity, this, data)
//                } else {
//                    matchDetailCoroutine.removeFromFavorite(pbProgressDetailActivity, this, data)
//                }
//
                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun isLoad() {
        pbProgressDetailActivity.visibility = View.VISIBLE
    }

    override fun stopLoad() {
        pbProgressDetailActivity.visibility = View.GONE
    }

    override fun showResult(data: List<Match>?) {
        val listOfMatchData = Match(
            data?.get(0)?.id,
            data?.get(0)?.idEvent ?: "-",
            data?.get(0)?.dateEvent ?: "-",
            data?.get(0)?.idHomeTeam ?: "-",
            data?.get(0)?.strHomeTeam ?: "-",
            data?.get(0)?.intHomeScore ?: "-",
            data?.get(0)?.intHomeShots ?: "-",
            data?.get(0)?.strHomeGoalDetails ?: "-",
            data?.get(0)?.strHomeYellowCards ?: "-",
            data?.get(0)?.strHomeRedCards ?: "-",
            data?.get(0)?.strHomeLineupSubstitutes ?: "-",
            data?.get(0)?.idAwayTeam ?: "-",
            data?.get(0)?.strAwayTeam ?: "-",
            data?.get(0)?.intAwayScore ?: "-",
            data?.get(0)?.intAwayShots ?: "-",
            data?.get(0)?.strAwayGoalDetails ?: "-",
            data?.get(0)?.strAwayYellowCards ?: "-",
            data?.get(0)?.strAwayRedCards ?: "-",
            data?.get(0)?.strAwayLineupSubstitutes ?: "-"
        )


        tvMatchDate.text = listOfMatchData.dateEvent

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

    private fun setFavorite() {

        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_yes)
            Log.d("FAVORITETRUE", "added to favorite")
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_no)
            Log.d("FAVORITEFALSE", "removed from favorite")
        }

    }

}
