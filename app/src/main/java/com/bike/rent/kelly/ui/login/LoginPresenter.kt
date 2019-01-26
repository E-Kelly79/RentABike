package com.bike.rent.kelly.ui.login

import com.bike.rent.kelly.data.DataManager
import com.bike.rent.kelly.ui.base.BasePresenter
import com.bike.rent.kelly.ui.main.MainView

class LoginPresenter(private val mDataManager: DataManager) : BasePresenter<LoginView>() {

    private var mLoginView: LoginView? = null

    override fun attachView(view: LoginView) {
        super.attachView(view)
        mLoginView = view
    }

    override fun detachView() {
        super.detachView()
    }
}