package com.bike.rent.kelly.ui.bike

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter

class BikeListPresenter () : BasePresenter<BikeListView>() {

    private var mBikeListView: BikeListView? = null

    override fun attachView(view: BikeListView) {
        super.attachView(view)
        mBikeListView = view
    }

    override fun detachView() {
        super.detachView()
    }

}