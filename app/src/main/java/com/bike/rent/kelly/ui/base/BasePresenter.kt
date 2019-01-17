package com.bike.rent.kelly.ui.base

open class BasePresenter<T : MvpView> : Presenter<T> {
    var mvpView: T? = null
        private set

    val isViewAttached: Boolean
        get() = mvpView != null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
        RuntimeException("Should invoke Presenter.attachView(MvpView) before" + " the Presenter")
}
