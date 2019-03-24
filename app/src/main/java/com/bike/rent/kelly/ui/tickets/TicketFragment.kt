package com.bike.rent.kelly.ui.tickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.model.tickets.Ticket
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.ticket_fragment.*
import org.joda.time.DateTime

class TicketFragment : BaseFragment(){

    val PRICE = "PRICE"
    val dayPrice: Float = 5.00f
    val monthPrice: Float = 17.00f
    val yearPrice: Float = 120.00f
    lateinit var priceArguments: PreferencesHelper
    lateinit var mView: View
    var tickets: Ticket? = null
    var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        priceArguments = PreferencesHelper(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.ticket_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Tickets")

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dayTicketContainer.setOnClickListener {
            priceArguments.setPrefFloat(PRICE, dayPrice)
            baseActivity.loadCardPaymentFragment(arguments!!, false)
            date = DateTime.now().plusDays(1).toString("E dd/MM/yyyy HH:mm:ss")
            tickets = Ticket(ticketDay.text.toString(), ticketSubText.text.toString(), 5.00f, date)
            BaseActivity.WALLET_LIST.add(tickets!!)

        }

        monthTicketContainer.setOnClickListener {
            priceArguments.setPrefFloat(PRICE, monthPrice)
            baseActivity.loadCardPaymentFragment(arguments!!, false)
            date =  DateTime.now().plusMonths(1).toString("E dd/MM/yyyy HH:mm:ss")
            tickets = Ticket(ticketMonth.text.toString(), monthTicketSubText.text.toString(), 17.00f, date)
            BaseActivity.WALLET_LIST.add(tickets!!)
        }

        yearTicketContainer.setOnClickListener {
            priceArguments.setPrefFloat(PRICE, yearPrice)
            Log.i("TICKET YEAR", priceArguments.getPrefFloat(PRICE).toString())
            baseActivity.loadCardPaymentFragment(arguments!!, false)
            date =  DateTime.now().plusYears(1).toString("E dd/MM/yyyy HH:mm:ss")
            tickets = Ticket(ticketYear.text.toString(), yearTicketSubText.text.toString(), 120.00f, date)
            BaseActivity.WALLET_LIST.add(tickets!!)

        }


        dayTicketInformation.setOnClickListener {
            Toast.makeText(context!!, "Information button pressed", Toast.LENGTH_LONG).show()
        }

        monthTicketInformation.setOnClickListener {
            Toast.makeText(context!!, "Information button pressed", Toast.LENGTH_LONG).show()
        }

        yearTicketInformation.setOnClickListener {
            Toast.makeText(context!!, "Information button pressed", Toast.LENGTH_LONG).show()
        }
    }
}