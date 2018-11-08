package com.yohanesam.footballmatchschedule.Presenter.APIs

import com.yohanesam.footballmatchschedule.Model.TheResponces.MatchResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIServices {
    @GET("eventspastleague.php?id=4399")
    fun getLastMatch(): Observable<MatchResponse>

    @GET("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4399")
     fun getNextMatch(): Observable<MatchResponse>

    companion object Factory {
        fun create() : APIServices {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.thesportsdb.com/api/v1/json/1/")
                .build()

            return retrofit.create(APIServices::class.java)
        }
    }
}