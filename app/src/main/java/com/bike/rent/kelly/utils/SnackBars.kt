package com.bike.rent.kelly.utils

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView

class SnackBars {
    companion object {
        fun centerSnackbar(view: View, message: String) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackbar.show()
            val snackbarView = snackbar.view
            val txtv = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            txtv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

}