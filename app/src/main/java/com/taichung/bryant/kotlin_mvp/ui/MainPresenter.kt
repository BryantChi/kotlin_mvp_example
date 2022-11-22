package com.taichung.bryant.kotlin_mvp.ui

import com.taichung.bryant.kotlin_mvp.models.UserModel

class MainPresenter(private var userView: UserView?, private val mainInteractor: MainInteractor) :
    MainInteractor.OnDataListener {

    fun getUserList() {
        userView?.showProgress()
        mainInteractor
    }

    fun onDestroy() {
        userView = null
    }

    override fun onSuccess(usersData: List<UserModel>) {
        userView?.hideProgress()
        userView?.setUsersData(usersData)
    }

    override fun onFail(strError: String) {
        userView?.hideProgress()
        userView?.getDataFailed(strError)
    }
}
