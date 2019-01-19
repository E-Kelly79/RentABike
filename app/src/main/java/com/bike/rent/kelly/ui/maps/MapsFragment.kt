package com.bike.rent.kelly.ui.maps

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.map_fragment.spinner_city
import java.text.FieldPosition

class MapsFragment : BaseFragment() {


    lateinit var mView: View
    lateinit var mTitle: TextView
    lateinit var mSubTitle: TextView
    lateinit var mContinueBtn: Button
    lateinit var mCitySpinner: Spinner
    lateinit var contractName: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.map_fragment, container, false)
        baseActivity.hideToolbar()
        mTitle = mView.findViewById(R.id.text_title) as TextView
        mSubTitle = mView.findViewById(R.id.text_title_sub) as TextView
        mContinueBtn = mView.findViewById(R.id.btn_landing) as Button
        mCitySpinner = mView.findViewById(R.id.spinner_city) as Spinner

        var mSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.city_array,
            android.R.layout.simple_spinner_item)
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mCitySpinner.adapter = mSpinnerAdapter

        mCitySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!mCitySpinner.selectedItem.equals("Please Select")){
                    mContinueBtn.isEnabled = true
                    val fragment = Fragment()
                    contractName = Bundle()
                    contractName.putString("contractName", mCitySpinner.selectedItem.toString())
                    fragment.arguments = contractName

                }else{
                    mContinueBtn.isEnabled = false
                }
                Toast.makeText(context, mCitySpinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
            }
        }
        mContinueBtn.setOnClickListener {
            baseActivity.loadBikeListFragment(contractName, false)
        }
        return mView
    }




}


