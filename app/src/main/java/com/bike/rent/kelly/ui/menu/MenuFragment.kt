package com.bike.rent.kelly.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.toolbar.mToolbarHome

class MenuFragment : BaseFragment() {

    lateinit var mView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.menu_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.mToolbarHome.setOnClickListener {
           baseActivity.showNavDrawer() }
        return mView
    }
}
