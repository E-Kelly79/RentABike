package com.bike.rent.kelly.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicLong

abstract class BaseNoNavMenuActivity : AppCompatActivity(), MvpView {
    private val mActivityId: Long = 0

    override val context: Context
        get() = applicationContext

    override val baseActivity: BaseActivity
        get() = this.context as BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
    }
}
