package com.yohanesam.footballmatchschedule.View.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.Entity.Match
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.Coroutines.MatchCoroutine
import com.yohanesam.footballmatchschedule.R
import com.yohanesam.footballmatchschedule.View.Activities.DetailOfTheMatch
import com.yohanesam.footballmatchschedule.View.Adapter.MatchRecycleAdapter
import com.yohanesam.footballmatchschedule.View.Util.MatchView
import kotlinx.android.synthetic.main.util_fragment_activity.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class NextMatchFragment : Fragment(), MatchView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var matchCoroutine: MatchCoroutine
    private lateinit var adapter: MatchRecycleAdapter
    private var matches: MutableList<Match> = mutableListOf()

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        srlPullNavigateUpRefresh.setOnRefreshListener(this)
        srlPullNavigateUpRefresh.setColorSchemeResources(

            android.R.color.holo_blue_dark,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_red_dark

        )


        adapter = MatchRecycleAdapter(this.context!!, matches) {
            startActivity<DetailOfTheMatch>(
                "ID_MATCH" to it.idEvent, "ID_HOME_TEAM" to it.idHomeTeam, "ID_AWAY_TEAM" to it.idAwayTeam
            )
        }

        rvMatchRecycleView.layoutManager = LinearLayoutManager(activity)
        rvMatchRecycleView.adapter = adapter

        val req = APIRepository()
        val gson = Gson()

        matchCoroutine = MatchCoroutine(this, req, gson)
        matchCoroutine.getMatchList(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.util_fragment_activity, container, false)

    }

    override fun onRefresh() {

        matchCoroutine.getMatchList(true)

    }

    override fun isLoad() {

        pbProgressLoading.visibility = View.VISIBLE

    }

    override fun stopLoad() {

        pbProgressLoading.visibility = View.GONE

    }

    override fun showResult(data: List<Match>?) {

        srlPullNavigateUpRefresh.isRefreshing = false
        matches.clear()

        data?.let {
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast("data = $data")

    }

}