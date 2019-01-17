package com.bike.rent.kelly.ui.main

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter

class MainPresenter(private val mDataManager: DataManager) : BasePresenter<MainView>() {
    private var mMainView: MainView? = null

    override fun attachView(view: MainView) {
        super.attachView(view)
        mMainView = view
    }

    override fun detachView() {
        super.detachView()
    }
}
