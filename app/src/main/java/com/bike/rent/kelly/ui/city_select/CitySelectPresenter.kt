package com.bike.rent.kelly.ui.city_select

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter

class CitySelectPresenter () : BasePresenter<CitySelectView>() {

    private var mCitySelectView: CitySelectView? = null

    override fun attachView(view: CitySelectView) {
        super.attachView(view)
        mCitySelectView = view
    }

    override fun detachView() {
        super.detachView()
    }
}