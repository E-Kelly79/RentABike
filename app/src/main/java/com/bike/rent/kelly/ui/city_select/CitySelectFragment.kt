package com.bike.rent.kelly.ui.city_select

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.base_layout.mivToolbarPrimary
import kotlinx.android.synthetic.main.base_layout.signinEmail
import kotlinx.android.synthetic.main.base_layout.signinImg
import kotlinx.android.synthetic.main.base_layout.signinName
import kotlinx.android.synthetic.main.map_fragment.mCitySpinner
import kotlinx.android.synthetic.main.map_fragment.mLandingBtn

class CitySelectFragment : BaseFragment() {
    lateinit var mView: View
    lateinit var contractName: Bundle
    lateinit var prefs: PreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.map_fragment, container, false)
        baseActivity.hideToolbar()
        baseActivity.lockNavDrawer()
        prefs = PreferencesHelper(context!!)
        baseActivity.signinName.text = prefs.getPrefString("Google_Name")
        baseActivity.signinEmail.text = prefs.getPrefString("Google_Email")
        Glide.with(context).load(prefs.getPrefString("Google_Photo")).into(baseActivity.signinImg)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseActivity.setStatusBarColor(R.color.color_primary, android.R.color.transparent,
            View.SYSTEM_UI_LAYOUT_FLAGS
        )

        baseActivity.mivToolbarPrimary.setOnClickListener {
            baseActivity.loadCitySelectFragment(arguments, false)
        }
        //setup spinner
        var mSpinnerAdapter = ArrayAdapter.createFromResource(context!!, R.array.city_array,
            android.R.layout.simple_spinner_item)
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mCitySpinner.adapter = mSpinnerAdapter

        //Listener for spinner to get selected item from spinner and send to bike list fragment
        mCitySpinner!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //All ways an option to select so method is redundant
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!mCitySpinner!!.selectedItem.equals("Please Select")){
                    mLandingBtn.isEnabled = true
                    val fragment = Fragment()
                    contractName = Bundle()
                    contractName.putString("contractName", mCitySpinner!!.selectedItem.toString())
                    fragment.arguments = contractName
                }else{
                    mLandingBtn.isEnabled = false
                }
            }
        }
        mLandingBtn.setOnClickListener {
            baseActivity.loadBikeListFragment(contractName, false)
        }

    }
}
