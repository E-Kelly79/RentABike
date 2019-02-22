package com.bike.rent.kelly.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.res.ResourcesCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.bike.rent.kelly.R

class ProgressDialog {

    companion object {
        var title: TextView? = null
        var loading: TextView? = null
        var body: TextView? = null
        var footerLink: TextView? = null
        var progress: ProgressBar? = null
        var btn: Button? = null
        fun progressDialog(context: Context): Dialog {

            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.prgress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            loading!!.visibility = View.VISIBLE
            progress!!.visibility = View.VISIBLE
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }

        fun deleteDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.prgress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(true)
            footerLink!!.text = context.getString(R.string.dilaog_close)
            btn!!.text = context.getString(R.string.dialog_delete_btn)
            btn!!.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_light))
            btn!!.setTextColor(context.resources.getColor(R.color.color_white))
            body!!.text = context.getString(R.string.dialog_delete)
            title!!.visibility = View.VISIBLE
            body!!.visibility = View.VISIBLE
            btn!!.visibility = View.VISIBLE
            footerLink!!.visibility = View.VISIBLE
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }
    }
}