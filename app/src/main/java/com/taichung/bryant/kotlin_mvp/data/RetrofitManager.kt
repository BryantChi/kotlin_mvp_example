package com.taichung.bryant.kotlin_mvp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {

    private val retrofit: Retrofit
    private val okHttpClient = OkHttpClient()
    private val interceptor = HttpLoggingInterceptor()

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient.newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(interceptor) // / show all JSON in logCat
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
//                        .addHeader("Authorization", AUTH)
                    .addHeader("Content-Type", "application/json")
//                            .method(original.method(), original.body())

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    companion object {
        private val manager = RetrofitManager()
        val client: Retrofit
            get() = manager.retrofit
    }
}