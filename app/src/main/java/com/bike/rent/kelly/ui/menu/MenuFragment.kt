package com.bike.rent.kelly.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment

class MenuFragment : BaseFragment() {

    lateinit var mView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.menu_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.mivToolbarPrimary?.setOnClickListener {
           baseActivity.showNavDrawer() }
        return mView
    }
}
