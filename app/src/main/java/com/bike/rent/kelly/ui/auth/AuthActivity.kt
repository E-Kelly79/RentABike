package com.bike.rent.kelly.ui.auth

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment

class AuthActivity: BaseFragment() {

    lateinit var mView: View
    @BindView(R.id.viewPager) @JvmField var mViewPager: ViewPager? = null
    @BindView(R.id.tab_layout_id) @JvmField var mTabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.auth_activity, container, false)
        baseActivity.hideToolbar()
        baseActivity.lockNavDrawer()
        ButterKnife.bind(this, mView)
        mViewPager!!.adapter = AuthPagerAdapter(childFragmentManager)
        mTabLayout!!.setupWithViewPager(mViewPager)
        mTabLayout!!.setTabTextColors(R.color.color_grey, R.color.color_primary)
        return mView
    }
}