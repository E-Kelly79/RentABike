package com.bike.rent.kelly.ui

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R

import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.ui.tickets.WalletAdapter
import com.marcoscg.dialogsheet.DialogSheet
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
        walletAdapter = WalletAdapter(BaseActivity.WALLET_LIST, context!!){row ->
//            val title = BaseActivity.WALLET_LIST[row].title
//            val sub= BaseActivity.WALLET_LIST[row].info
//            val price = BaseActivity.WALLET_LIST[row].price
                activateTicketCheck()
        }

        layoutManager = LinearLayoutManager(context)
        mWalletRecyclerView.layoutManager = layoutManager
        mWalletRecyclerView.adapter = walletAdapter
        walletAdapter!!.notifyDataSetChanged()
    }

    fun activateTicketCheck() {
        val dialogSheet: DialogSheet = DialogSheet(context)
        dialogSheet.setTitle(R.string.dialog_activate_title)
            .setMessage(R.string.dialog_activate)
            .setCancelable(true)
            .setPositiveButton(R.string.dialog_ok_btn) {
            }
            .setRoundedCorners(false)
            .setBackgroundColor(Color.WHITE)
            .setButtonsColorRes(R.color.color_primary)
            .show()
    }
}