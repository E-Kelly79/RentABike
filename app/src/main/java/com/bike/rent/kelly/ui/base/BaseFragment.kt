package com.bike.rent.kelly.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.bike.rent.kelly.ui.base.BaseActivity

open class BaseFragment : Fragment() {
    private var mActivity: BaseActivity? = null

    val baseArguments: Bundle?
        get() = arguments

    val baseActivity: BaseActivity
        get() {
            if (mActivity == null) {
                mActivity = activity as BaseActivity?
            }
            return mActivity!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = if (activity != null) activity as BaseActivity? else context
            this.mActivity = activity
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun onBackPressed() {
        mActivity!!.onBackPressed()
    }

    /**
     * Do nothing, fragment could override this method to get notifications on user interaction from baseActivity
     */
    fun onUserInteraction() {
    }

    fun tag(): String {
        return ""
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
