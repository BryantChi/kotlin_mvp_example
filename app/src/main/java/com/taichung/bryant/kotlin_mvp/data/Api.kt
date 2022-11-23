package com.taichung.bryant.kotlin_mvp.data

import com.taichung.bryant.kotlin_mvp.models.UserModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("users")
    fun getUserList(
        @Query("since") since: Int? = null,
        @Query("per_page") perPage: Int? = null
    ): Observable<List<UserModel>>
}
