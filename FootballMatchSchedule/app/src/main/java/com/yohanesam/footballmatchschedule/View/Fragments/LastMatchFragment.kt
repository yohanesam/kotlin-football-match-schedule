package com.yohanesam.footballmatchschedule.View.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.Entity.Match
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.Coroutines.MatchCoroutine
import com.yohanesam.footballmatchschedule.R
import com.yohanesam.footballmatchschedule.View.Adapter.MatchRecycleAdapter
import com.yohanesam.footballmatchschedule.View.Util.MatchUtil
import kotlinx.android.synthetic.main.util_fragment_activity.*
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment : Fragment(), MatchUtil, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var matchCoroutine: MatchCoroutine
    private lateinit var adapter: MatchRecycleAdapter
    private var matches: MutableList<Match> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        srlPullNavigateUpRefresh.setOnRefreshListener(this)
        adapter = MatchRecycleAdapter(this.context!!, matches)

        rvMatchRecycleView.layoutManager = LinearLayoutManager(activity)
        rvMatchRecycleView.adapter = adapter

        val req = APIRepository()
        val gson = Gson()

        matchCoroutine = MatchCoroutine(this, req, gson)
        matchCoroutine.getMatchList(false)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.util_fragment_activity, container, false)
    }

    override fun isLoad() {
        pbProgressLoading.visibility = View.VISIBLE
}

    override fun stopLoad() {
        pbProgressLoading.visibility = View.GONE
    }

    override fun onRefresh() {
        matchCoroutine.getMatchList(false)
    }

    override fun showResult(data: List<Match>?) {
//        srlPullNavigateUpRefresh.isRefreshing = false
        matches.clear()

        data?.let {
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast("data = $data")
    }

}