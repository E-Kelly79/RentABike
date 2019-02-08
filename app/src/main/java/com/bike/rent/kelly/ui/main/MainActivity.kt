package com.bike.rent.kelly.ui.main

import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.bike.rent.kelly.ui.base.BaseActivity
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : BaseActivity() {
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val args = Bundle()
        baseActivity.loadAuthFragment(args, false)
    }
}
