package com.bike.rent.kelly.ui.wallet

import android.content.Context
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.tickets.Ticket
import com.bike.rent.kelly.ui.wallet.WalletAdapter.ViewHolder
import org.joda.time.DateTime
import java.lang.Exception


class WalletAdapter(private var walletList:ArrayList<Ticket>, private val context: Context,
        val listener: (Int) -> Unit): RecyclerView.Adapter<ViewHolder>() {

        lateinit var mRecyclerView: RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.wallet_list_row, parent, false)
        return ViewHolder(view)
    }





    override fun getItemCount(): Int {
        return walletList.size
    }

    @RequiresApi(VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindView(walletList[position], position, listener)
        if (DateTime.now().isAfter(walletList[position].expires!!)) {
            walletList.removeAt(position)
            mRecyclerView.post(object : Runnable {
                override fun run() {
                   notifyDataSetChanged()
                }
            })
        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.mWalletTitle)!!
        var sub = itemView.findViewById<TextView>(R.id.mWalletSub)!!
        var price = itemView.findViewById<TextView>(R.id.mWalletPrice)!!
        var expiresAt = itemView.findViewById<TextView>(R.id.mTime)!!
        var parentLayout = itemView.findViewById<ConstraintLayout>(R.id.mWalletContainer)!!

        @RequiresApi(VERSION_CODES.N)
        fun bindView(ticket: Ticket, pos: Int, listener: (Int) -> Unit) = with(itemView) {
            title.text = "${ticket.title}"
            sub.text = "${ticket.info}"
            price.text = "Price £${ticket.price}"
            expiresAt.text = "Expires ${ticket.expires!!.toString("E dd/MM/yyyy HH:mm:ss")}"


            if (ticket.activated){
                expiresAt.visibility = View.VISIBLE
                expiresAt.setTextColor(resources.getColor(R.color.color_danger))
                title.text = "Activated ${ticket.title}"
                parentLayout.setOnClickListener(null)
            }else {
                parentLayout.setOnClickListener {
                    listener(pos)
                    ticket.activated = true
                    expiresAt.setTextColor(resources.getColor(R.color.color_danger))
                    expiresAt.visibility = View.VISIBLE
                    title.text = "Activated ${ticket.title}"

                }
            }
        }
    }
}