package com.bike.rent.kelly.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment

class MapsFragment : BaseFragment() {


    lateinit var mView: View
    lateinit var mTitle: TextView
    lateinit var mSubTitle: TextView
    lateinit var mContinueBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.map_fragment, container, false)
        baseActivity.hideToolbar()
        mTitle = mView.findViewById(R.id.text_title) as TextView
        mSubTitle = mView.findViewById(R.id.text_title_sub) as TextView
        mContinueBtn = mView.findViewById(R.id.btn_landing) as Button

        mContinueBtn.setOnClickListener {
            baseActivity.loadMenuFragment(baseArguments!!, false);
        }

        return mView
    }
}
