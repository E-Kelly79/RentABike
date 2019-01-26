package com.bike.rent.kelly.ui.maps

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter

class GoogleMapsPresenter (private val mDataManager: DataManager) : BasePresenter<GoogleMapsView>() {

    private var mGoogleMapsView: GoogleMapsView? = null

    override fun attachView(view: GoogleMapsView) {
        super.attachView(view)
        mGoogleMapsView = view
    }

    override fun detachView() {
        super.detachView()
    }
}