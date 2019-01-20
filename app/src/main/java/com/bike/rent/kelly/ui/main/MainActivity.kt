package com.bike.rent.kelly.ui.main

import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.bike.rent.kelly.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = Bundle()
        baseActivity.loadLoginFragment(args, false)
    }
}
