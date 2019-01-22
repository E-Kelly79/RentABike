package com.bike.rent.kelly.ui.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment

class LoginFragment: BaseFragment(){

    lateinit var mView: View
    lateinit var contractName: Bundle
    lateinit var mLoginBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.login_fragment, container, false)
        mLoginBtn = mView.findViewById(R.id.login_button)
        mLoginBtn.setOnClickListener {
            val fragment = Fragment()
            contractName = Bundle()
            contractName.putString("contractName", "Hello")
            fragment.arguments = contractName
            Toast.makeText(context, "hello", Toast.LENGTH_LONG).show()
           baseActivity.loadMapsFragment(contractName, false)
        }
        return mView
    }

}