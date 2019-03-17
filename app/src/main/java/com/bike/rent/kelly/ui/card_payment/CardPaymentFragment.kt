package com.bike.rent.kelly.ui.card_payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.card_payment_fragment.*

class CardPaymentFragment: BaseFragment() {

    val VISA_CARD = "4319474747474747"
    val MASTERCARD = "5139414141414141"
    val VISA_CCV = "999"
    val MASTER_CCV = "555"
    lateinit var mView: View
    lateinit var prices: PreferencesHelper


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

        payNowBtn.setOnClickListener {
            if(cardNumber.text.toString() == VISA_CARD || cardNumber.text.toString() == MASTERCARD) {
                BaseActivity.CREDIT_AMOUNT -= prices.getPrefFloat("PRICE").toString().toFloat()
                Log.i("CREDIT", BaseActivity.CREDIT_AMOUNT.toString())

            }
        }

        getUserInput()
        cardNumber.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (cardNumber.text.toString().contains(VISA_CARD.substring(0,4))){
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visa, 0, 0, 0)
                }else if (cardNumber.text.toString().contains(MASTERCARD.substring(0,4))) {
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mastercard, 0, 0, 0)
                }
            }
        })
    }

    private fun getUserInput() {
        payNowBtn.setOnClickListener {
            if(cardNumber.text.toString() == VISA_CARD || cardNumber.text.toString() == MASTERCARD) {
                BaseActivity.CREDIT_AMOUNT -= prices.getPrefFloat("PRICE").toString().toFloat()
                Log.i("TAG", "AMOUNT = ${BaseActivity.CREDIT_AMOUNT}")
            }else{
                Toast.makeText(context!!, "Please make sure your card number is correct", Toast.LENGTH_LONG).show()
                cardNumber.isFocusable
            }
        }


//            if (CCV.toInt() != VISA_CCV || CCV.toInt() != MASTER_CCV) {
//                Toast.makeText(context!!, "Please make sure your CCV number is correct!!", Toast.LENGTH_SHORT).show()
//                cardCCV.setTextColor(Color.RED)
//                payNowBtn.isEnabled = false
//            } else {
//                payNowBtn.isEnabled = true
//                cardCCV.setTextColor(Color.GREEN)
//            }

    }
}

private fun EditText.onChange(char: (String) -> Unit) {
   this.addTextChangedListener(object: TextWatcher{
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

