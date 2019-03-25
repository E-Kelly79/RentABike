package com.bike.rent.kelly.ui.wallet


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bike.rent.kelly.R

import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.wallet_fragment.mWalletRecyclerView

class WalletFragment : BaseFragment(){

    lateinit var mView: View
    var walletAdapter: WalletAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(com.bike.rent.kelly.R.layout.wallet_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("My Wallet")
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        walletAdapter = WalletAdapter(BaseActivity.WALLET_LIST, context!!) { row ->
            activateTicketCheck()
        }

        layoutManager = LinearLayoutManager(context)
        mWalletRecyclerView.layoutManager = layoutManager
        mWalletRecyclerView.adapter = walletAdapter
        walletAdapter!!.notifyDataSetChanged()
    }

    fun activateTicketCheck() {
        SweetAlertDialog(baseActivity, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("WARNING!!")
            .setContentText(getString(R.string.dialog_activate))
            .show()
    }

}