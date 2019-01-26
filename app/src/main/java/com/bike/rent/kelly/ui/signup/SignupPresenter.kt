package com.bike.rent.kelly.ui.signup

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter

class SignupPresenter (private val mDataManager: DataManager) : BasePresenter<SignupView>() {

    private var mSignupView: SignupView? = null

    override fun attachView(view: SignupView) {
        super.attachView(view)
        mSignupView = view
    }

    override fun detachView() {
        super.detachView()
    }
}