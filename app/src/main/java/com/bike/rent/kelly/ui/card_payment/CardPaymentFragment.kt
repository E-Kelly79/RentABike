package com.bike.rent.kelly.ui.card_payment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.model.tickets.Ticket
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.utils.ProgressDialog
import kotlinx.android.synthetic.main.card_payment_fragment.*
import java.util.Calendar

class CardPaymentFragment: BaseFragment() {

    val VISA_CARD = "4319474747474747"
    val MASTERCARD = "5139414141414141"
    val VISA_CCV = "999"
    val MASTER_CCV = "555"
    lateinit var mView: View
    lateinit var prices: PreferencesHelper
    lateinit var calendar: Calendar
    lateinit var datePicker:DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prices = PreferencesHelper(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.card_payment_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Card Payment")
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cardType.text = "We accept Visa & Mastercard only"
        when {
            prices.getPrefFloat("PRICE") == 5.0f -> {
                Log.i("DAY", prices.getPrefFloat("PRICE").toString())
                totalAmount.text = "${prices.getPrefFloat("PRICE").toString()}"
            }
            prices.getPrefFloat("PRICE") == 17.0f -> {
                totalAmount.text = "£${prices.getPrefFloat("PRICE").toString()}"
                Log.i("MONTH", prices.getPrefFloat("PRICE").toString())
            }
            prices.getPrefFloat("PRICE") == 120.0f -> {
                totalAmount.text = "£${prices.getPrefFloat("PRICE").toString()}"
                Log.i("YEAR", prices.getPrefFloat("PRICE").toString())
            }
            else -> Log.i("NONE", "No price has been set")
        }

        if (cardExpiry.isFocused){
            baseActivity.closeSoftKeyboard()
        }
        cardExpiry.setOnClickListener {
            calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            var month = calendar.get(Calendar.MONTH)
            var year = calendar.get(Calendar.YEAR)

            if (VERSION.SDK_INT >= VERSION_CODES.N) {
                datePicker = DatePickerDialog(context, OnDateSetListener { _, mYear, mMonth, _ ->
                    if (mMonth >= 0){
                        var expiryMonth = mMonth + 1
                        var format = String.format("%02d", expiryMonth)
                        cardExpiry.setText("$format/$mYear")
                    }

                },year, month, day)
                datePicker.show()
            }
        }



        payNowBtn.setOnClickListener {
            checkCardDetails()
            if (cardNumber.text.toString() == VISA_CARD || cardNumber.text.toString() == MASTERCARD) {
                if (cardCCV.text.toString() == VISA_CCV || cardCCV.text.toString() == MASTER_CCV) {
                    BaseActivity.CREDIT_AMOUNT -= prices.getPrefFloat("PRICE").toString().toFloat()
                    Log.i("CREDIT", BaseActivity.CREDIT_AMOUNT.toString())
                } else {
                    Toast.makeText(context!!, "Please make sure your CCV number is correct!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }




        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (cardNumber.text.toString().contains(VISA_CARD.substring(0, 4))) {
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visa, 0, 0, 0)
                } else if (cardNumber.text.toString().contains(MASTERCARD.substring(0, 4))) {
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mastercard, 0, 0, 0)
                }
            }
        })
    }

    private fun checkCardDetails() {
            if (cardNumber.text.toString() == VISA_CARD || cardNumber.text.toString() == MASTERCARD) {
                if (cardCCV.text.toString() == VISA_CCV || cardCCV.text.toString() == MASTER_CCV) {
                    BaseActivity.CREDIT_AMOUNT -= prices.getPrefFloat("PRICE").toString().toFloat()
                    Log.i("CREDIT", BaseActivity.CREDIT_AMOUNT.toString())
                    Handler().postDelayed({
                        ProgressDialog.progressDialog(context!!)
                    }, 2500)
                    Toast.makeText(context!!, "Payment was successful", Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        baseActivity.loadWalletFragment(baseActivity.getArguments(), false)
                    }, 2500)
                } else {
                    Toast.makeText(context!!, "Please make sure your CCV number is correct!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(context!!, "Please make sure your card number is correct!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun EditText.onChange(char: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}

