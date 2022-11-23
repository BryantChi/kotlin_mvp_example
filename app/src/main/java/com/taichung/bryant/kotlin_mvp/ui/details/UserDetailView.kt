package com.taichung.bryant.kotlin_mvp.ui.details

import com.taichung.bryant.kotlin_mvp.models.UserDeailModel

interface UserDetailView {
    fun showProgress()
    fun hideProgress()
    fun setUserData(user: UserDeailModel)
    fun getDataFailed(strError: String)
}