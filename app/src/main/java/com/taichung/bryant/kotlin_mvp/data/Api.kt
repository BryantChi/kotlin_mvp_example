package com.taichung.bryant.kotlin_mvp.data

import com.taichung.bryant.kotlin_mvp.models.UserDeailModel
import com.taichung.bryant.kotlin_mvp.models.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface Api {
    @GET("users")
    fun getUserList(
        @Query("since") since: Int? = null,
        @Query("per_page") perPage: Int? = null
    ): Observable<List<UserModel>>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Observable<UserDeailModel>
}
