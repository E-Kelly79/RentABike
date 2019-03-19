package com.bike.rent.kelly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.ui.tickets.WalletAdapter

class WalletFragment : BaseFragment(){

    lateinit var mView: View
    var walletAdapter: WalletAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}