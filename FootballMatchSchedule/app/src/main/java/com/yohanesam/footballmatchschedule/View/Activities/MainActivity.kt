package com.yohanesam.footballmatchschedule.View.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yohanesam.footballmatchschedule.R
import com.yohanesam.footballmatchschedule.View.Adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the fragment function
        setFragment()
    }

    private fun setFragment() {
        pagerAdapter = FragmentAdapter(supportFragmentManager)
        vpMainViewPager.adapter = pagerAdapter
        tlMainTabLayout.setupWithViewPager(vpMainViewPager)
    }
}
