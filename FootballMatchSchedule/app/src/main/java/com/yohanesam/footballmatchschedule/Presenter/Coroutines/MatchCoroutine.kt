package com.yohanesam.footballmatchschedule.Presenter.Coroutines

import android.util.Log
import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.TheResponces.MatchResponse
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.APIs.SportAPI
import com.yohanesam.footballmatchschedule.View.Util.MatchUtil
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchCoroutine (private val view: MatchUtil, private val apiRepository: APIRepository, private val gson: Gson) {
    fun getMatchList(isNextMatch: Boolean): Unit {

        view.isLoad()

        if (!isNextMatch!!) {
            async(UI) {
                val data = bg {
                    gson.fromJson(
                        apiRepository.doRequest(SportAPI.getLastMatch()),
                        MatchResponse::class.java
                    )
                }

                Log.d("TAG", data.await().event.toString())
                view.showResult(data.await().event)
                view.stopLoad()
            }
        }

        else {
            async(UI) {
                val data = bg {
                    gson.fromJson(
                        apiRepository.doRequest(SportAPI.getNextMatch()),
                        MatchResponse::class.java
                    )
                }

                view.showResult(data.await().event)
                view.stopLoad()
            }
        }
    }
}