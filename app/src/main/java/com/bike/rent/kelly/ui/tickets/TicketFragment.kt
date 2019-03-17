package com.bike.rent.kelly.ui.tickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.ticket_fragment.*

class TicketFragment : BaseFragment(){

    val PRICE = "PRICE"
    val dayPrice: Float = 5.00f
    val monthPrice: Float = 17.00f
    val yearPrice: Float = 120.00f
    lateinit var priceArguments: PreferencesHelper
    lateinit var mView: View

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
            Log.i("TICKET DAY", priceArguments.getPrefFloat(PRICE).toString())
            baseActivity.loadCardPaymentFragment(arguments!!, false)
        }

        monthTicketContainer.setOnClickListener {
            priceArguments.setPrefFloat(PRICE, monthPrice)
            Log.i("TICKET MONTH",priceArguments.getPrefFloat(PRICE).toString())
            baseActivity.loadCardPaymentFragment(arguments!!, false)
        }

        yearTicketContainer.setOnClickListener {
            priceArguments.setPrefFloat(PRICE, yearPrice)
            Log.i("TICKET YEAR", priceArguments.getPrefFloat(PRICE).toString())
            baseActivity.loadCardPaymentFragment(arguments!!, false)
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