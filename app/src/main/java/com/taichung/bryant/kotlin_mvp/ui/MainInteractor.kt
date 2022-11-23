package com.taichung.bryant.kotlin_mvp.ui

import com.taichung.bryant.kotlin_mvp.models.UserModel

class MainInteractor {

    interface OnDataListener {
        fun onSuccess(usersData: List<UserModel>)
        fun onUpdateSuccess(usersData: List<UserModel>)
        fun onFail(strError: String)
        fun onGetUserList(since: Int, prepage: Int)
    }
}