package com.taichung.bryant.kotlin_mvp.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.taichung.bryant.kotlin_mvp.R
import com.taichung.bryant.kotlin_mvp.models.UserDeailModel
import com.taichung.bryant.kotlin_mvp.ui.MainActivity
import com.taichung.bryant.kotlin_mvp.utils.showToast
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity(), UserDetailView {
    private lateinit var userDetailPresenter: UserDetailPresenter
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        userName = intent.getStringExtra(MainActivity.USER_NAME_KEY).toString()

        userDetailPresenter = UserDetailPresenter(this)
        progressBar.visibility = View.GONE
        userDetailPresenter.getUserData(userName)
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.title = null
            it.setHomeAsUpIndicator(R.drawable.close)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setUserData(user: UserDeailModel) {
        im_user_photo.load(user.avatar_url) {
            crossfade(true)
            placeholder(R.drawable.man)
            transformations(CircleCropTransformation())
        }
        tv_user_login.text = user.login
        tv_user_name.text = user.name
        tv_location.text = user.location
        tv_blog.text = user.blog
    }

    override fun getDataFailed(strError: String) {
        showToast(applicationContext, strError)
    }

    override fun onDestroy() {
        userDetailPresenter.onDestroy()
        super.onDestroy()
    }
}
