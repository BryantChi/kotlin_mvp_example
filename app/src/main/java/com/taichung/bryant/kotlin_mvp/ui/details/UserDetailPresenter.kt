package com.taichung.bryant.kotlin_mvp.ui.details

import com.taichung.bryant.kotlin_mvp.data.Api
import com.taichung.bryant.kotlin_mvp.data.RetrofitManager
import com.taichung.bryant.kotlin_mvp.models.UserDeailModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDetailPresenter(
    private var userDetailView: UserDetailView?
) : UserDetailInteractor.OnDataListener {

    private val userDataService = RetrofitManager.client.create(Api::class.java)

    fun getUserData(userName: String) {
        userDetailView?.showProgress()
        onGetUser(userName)
    }

    fun onDestroy() {
        userDetailView = null
    }

    override fun onSuccess(userData: UserDeailModel) {
        userDetailView?.hideProgress()
        userDetailView?.setUserData(userData)
    }

    override fun onFail(strError: String) {
        userDetailView?.hideProgress()
        userDetailView?.getDataFailed(strError)
    }

    override fun onGetUser(userName: String) {
        userDataService.getUser(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it)
            }, {
                onFail("Error")
                throw it
            })
    }
}
