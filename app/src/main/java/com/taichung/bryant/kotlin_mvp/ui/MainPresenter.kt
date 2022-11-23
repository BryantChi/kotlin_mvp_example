package com.taichung.bryant.kotlin_mvp.ui

import android.util.Log
import com.taichung.bryant.kotlin_mvp.data.Api
import com.taichung.bryant.kotlin_mvp.data.RetrofitManager
import com.taichung.bryant.kotlin_mvp.models.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var userView: UserView?, private val mainInteractor: MainInteractor) :
    MainInteractor.OnDataListener {

    private val usersDataService = RetrofitManager.client.create(Api::class.java)

    fun getUserList(since: Int, prepage: Int) {
        userView?.showProgress()
        onGetUserList(since, prepage)
    }

    fun onDestroy() {
        userView = null
    }

    override fun onSuccess(usersData: List<UserModel>) {
        userView?.hideProgress()
        userView?.setUsersData(usersData)
    }

    override fun onUpdateSuccess(usersData: List<UserModel>) {
        userView?.hideProgress()
        userView?.updateUsersData(usersData)
    }

    override fun onFail(strError: String) {
        userView?.hideProgress()
        userView?.getDataFailed(strError)
    }

    override fun onGetUserList(since: Int, prepage: Int) {
        usersDataService.getUserList(since, prepage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("it size", it.size.toString())
                it.forEachIndexed { index, userModel ->
                    Log.d("it id", userModel.id.toString() + "|" + index)
                }
                if (since == 0) {
                    onSuccess(it)
                } else {
                    onUpdateSuccess(it)
                }
            }, {
                onFail("Error")
                throw it
            })
    }


}
