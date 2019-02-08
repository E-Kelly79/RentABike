package com.bike.rent.kelly.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.auth_activity.*


class AuthActivity: BaseFragment() {

    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.auth_activity, container, false)
        baseActivity.hideToolbar()
        baseActivity.lockNavDrawer()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewPager.adapter = AuthPagerAdapter(childFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.setTabTextColors(resources.getColor(R.color.color_white), resources.getColor(R.color.color_white))
    }
}