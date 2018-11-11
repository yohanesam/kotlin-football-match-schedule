package com.yohanesam.footballmatchschedule.Presenter.Coroutines

import com.google.gson.Gson
import com.yohanesam.footballmatchschedule.Model.TheResponces.MatchJSONArray
import com.yohanesam.footballmatchschedule.Presenter.APIs.APIRepository
import com.yohanesam.footballmatchschedule.Presenter.APIs.SportAPI
import com.yohanesam.footballmatchschedule.View.Util.MatchView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

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

}