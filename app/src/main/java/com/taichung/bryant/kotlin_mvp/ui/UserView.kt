package com.taichung.bryant.kotlin_mvp.ui

import com.taichung.bryant.kotlin_mvp.models.UserModel

interface UserView {
    fun showProgress()
    fun hideProgress()
    fun setUsersData(usersList: List<UserModel>)
    fun getDataFailed(strError: String)
}