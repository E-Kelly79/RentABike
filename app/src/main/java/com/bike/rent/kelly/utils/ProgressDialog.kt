package com.bike.rent.kelly.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

import android.widget.TextView
import com.bike.rent.kelly.R

object ProgressDialog {
        fun progressDialog(context: Context): AlertDialog {
            val builder = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.prgress_dialog, null)
            var message = dialogView.findViewById<TextView>(R.id.text_loading)
            builder.setView(dialogView)
            builder.setCancelable(true)

            return builder.create()
        }

}