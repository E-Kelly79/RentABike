package com.bike.rent.kelly.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.login_fragment.login_button

class LoginFragment: BaseFragment(){

    lateinit var mView: View
    var mLoginBtn: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.login_fragment, container, false)
        baseActivity.hideToolbar()
        mLoginBtn = mView.findViewById(R.id.login_button) as Button
        mLoginBtn!!.setOnClickListener {
            baseActivity.loadMapsFragment(baseArguments!!, false)
        }
        return mView
    }

}