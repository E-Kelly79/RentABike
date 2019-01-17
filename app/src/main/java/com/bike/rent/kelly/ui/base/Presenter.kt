package com.bike.rent.kelly.ui.base

interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}
