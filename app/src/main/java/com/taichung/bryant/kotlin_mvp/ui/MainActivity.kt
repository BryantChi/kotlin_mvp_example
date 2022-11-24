package com.taichung.bryant.kotlin_mvp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taichung.bryant.kotlin_mvp.R
import com.taichung.bryant.kotlin_mvp.adapter.UserAdapter
import com.taichung.bryant.kotlin_mvp.listeners.ItemClickListener
import com.taichung.bryant.kotlin_mvp.models.UserModel
import com.taichung.bryant.kotlin_mvp.ui.details.UserDetailActivity
import com.taichung.bryant.kotlin_mvp.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserView {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var userAdapter: UserAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var mainUsersList: MutableList<UserModel> = mutableListOf()
    private var lastUsersList: MutableList<UserModel> = mutableListOf()
    private var lastVisibleItem: Int = 0

    companion object {
        const val USER_NAME_KEY = "username"
        const val LIMIT_PRE_PAGE = 20
        const val LIMIT_USERS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter = MainPresenter(this, MainInteractor())
        progressBar.visibility = View.GONE
        initView()
        mainPresenter.getUserList(0, LIMIT_PRE_PAGE)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setUsersData(usersList: List<UserModel>) {
        mainUsersList.addAll(usersList)
        userAdapter.submitList(usersList)

        lastUsersList.clear()
        lastUsersList.addAll(usersList)
    }

    override fun updateUsersData(usersList: List<UserModel>) {
        mainUsersList.addAll(usersList)
        userAdapter.updateList(usersList)

        lastUsersList.clear()
        lastUsersList.addAll(usersList)
        rv_users.smoothScrollToPosition(userAdapter.itemCount - 15)
    }

    override fun getDataFailed(strError: String) {
        showToast(applicationContext, strError)
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    private fun initView() {
        userAdapter = UserAdapter(
            applicationContext,
            object : ItemClickListener {
                override fun itemClick(userName: String) {
                    val intent = Intent(applicationContext, UserDetailActivity::class.java)
                    intent.putExtra(Companion.USER_NAME_KEY, userName)
                    startActivity(intent)
                }
            }
        )

        mLayoutManager = LinearLayoutManager(applicationContext)
        rv_users.apply {
            layoutManager = mLayoutManager
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val isScrollToBottom =
                        newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == userAdapter.itemCount
                    val isLoadMore =
                        lastUsersList.size == LIMIT_PRE_PAGE && mainUsersList.size <= LIMIT_USERS
                    val lastSince: Int = mainUsersList.last().id

                    if (isScrollToBottom) {
                        if (isLoadMore) {
                            mainPresenter.getUserList(lastSince, LIMIT_PRE_PAGE)
                        } else {
                            showToast(applicationContext, getString(R.string.no_more_info))
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                }
            })
        }
    }
}
