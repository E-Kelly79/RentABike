package com.bike.rent.kelly.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment

class MapsFragment : BaseFragment() {


     lateinit var mView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.map_fragment, container, false)
        return view
    }
}
