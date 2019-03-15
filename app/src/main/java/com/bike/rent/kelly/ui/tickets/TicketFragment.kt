package com.bike.rent.kelly.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.ticket_fragment.*

class TicketFragment : BaseFragment(){
    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.ticket_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Tickets")
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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