package com.bike.rent.kelly.ui.base

import android.content.Context
import com.bike.rent.kelly.ui.base.BaseActivity

interface MvpView {
    val context: Context
    val baseActivity: BaseActivity
}