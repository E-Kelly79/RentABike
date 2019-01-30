package com.bike.rent.kelly.ui.city_select

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class CitySelectFragment : BaseFragment() {
    lateinit var mView: View
    lateinit var mTitle: TextView
    lateinit var mSubTitle: TextView
    lateinit var mContinueBtn: Button
    lateinit var mCitySpinner: Spinner
    lateinit var contractName: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.map_fragment, container, false)
        baseActivity.hideToolbar()
        mTitle = mView.findViewById(R.id.text_title) as TextView
        mSubTitle = mView.findViewById(R.id.text_title_sub) as TextView
        mContinueBtn = mView.findViewById(R.id.btn_landing) as Button
        mCitySpinner = mView.findViewById(R.id.spinner_city) as Spinner
        BaseActivity.DB_FAVOURITES.setValue("HEllo Eoin")


        //setup spinner
        var mSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.city_array,
            android.R.layout.simple_spinner_item)
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mCitySpinner.adapter = mSpinnerAdapter

        //Listener for spinner to get selected item from spinner and send to bike list fragment
        mCitySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //All ways an option to select so method is redundant
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
            }
        }
        mContinueBtn.setOnClickListener {
            baseActivity.loadBikeListFragment(contractName, false)
        }
        return mView
    }
}
