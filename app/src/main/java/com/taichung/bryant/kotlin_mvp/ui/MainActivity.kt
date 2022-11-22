package com.taichung.bryant.kotlin_mvp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taichung.bryant.kotlin_mvp.R
import com.taichung.bryant.kotlin_mvp.adapter.UserAdapter
import com.taichung.bryant.kotlin_mvp.listeners.ItemClickListener
import com.taichung.bryant.kotlin_mvp.models.UserModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserView {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter = MainPresenter(this, MainInteractor())
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.getUserList()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setUsersData(usersList: List<UserModel>) {
        userAdapter = UserAdapter(
            applicationContext,
            object : ItemClickListener {
                override fun itemClick(position: Int) {
                    TODO("Not yet implemented")
                }
            }
        )
        userAdapter.submitList(usersList)
        rv_users.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userAdapter
        }
    }

    override fun getDataFailed(strError: String) {
        Toast.makeText(applicationContext, strError, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }
}
