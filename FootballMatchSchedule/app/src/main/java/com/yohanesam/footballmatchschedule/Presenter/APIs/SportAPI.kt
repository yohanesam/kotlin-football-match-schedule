package com.yohanesam.footballmatchschedule.Presenter.APIs

object SportAPI {
    fun getLastMatch() : String {
        return "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4399"
    }

    fun getNextMatch() : String {
        return "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4399"
    }
}