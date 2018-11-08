package com.yohanesam.footballmatchschedule.Presenter.APIs

import java.net.URL

class APIRepository {
    fun doRequest(url: String) : String {
        return URL(url).readText()
    }
}