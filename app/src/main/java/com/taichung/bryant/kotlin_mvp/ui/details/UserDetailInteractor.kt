package com.taichung.bryant.kotlin_mvp.ui.details

import com.taichung.bryant.kotlin_mvp.models.UserDeailModel

class UserDetailInteractor {
    interface OnDataListener {
        fun onSuccess(userData: UserDeailModel)
        fun onFail(strError: String)
        fun onGetUser(userName: String)
    }
}