package com.bike.rent.kelly.ui.tickets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.bike.Bike
import com.bike.rent.kelly.model.tickets.Ticket
import java.text.FieldPosition

class WalletAdapter(private var walletList:ArrayList<Ticket>, private val context: Context,
        val listener: (Int) -> Unit): RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WalletAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.bike_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return walletList.size
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
        holder?.bindView(walletList[position], position, listener)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.mWalletTitle)
        var sub = itemView.findViewById<TextView>(R.id.mWalletSub)
        var price = itemView.findViewById<TextView>(R.id.mWalletPrice)
        var parentLayout = itemView.findViewById<RelativeLayout>(R.id.mWalletContainer)

        fun bindView(ticket: Ticket, pos:Int, listener:(Int)-> Unit) = with(itemView){
            title.text = "${ticket.title}"
            sub.text = "Card Use: ${ticket.info}"
            price.text = "Price £${ticket.price.toString()}"
            parentLayout.setOnClickListener { listener(pos) }


    }
}