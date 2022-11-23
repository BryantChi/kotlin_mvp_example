package com.taichung.bryant.kotlin_mvp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taichung.bryant.kotlin_mvp.R
import com.taichung.bryant.kotlin_mvp.listeners.ItemClickListener
import com.taichung.bryant.kotlin_mvp.models.UserModel

class UserAdapter(var context: Context, var itemClick: ItemClickListener) :
    RecyclerView.Adapter<UserAdapter.Holder>() {

    var userList = mutableListOf<UserModel>()

    fun submitList(itemUser: List<UserModel>) {
        this.userList.clear()
        this.userList.addAll(itemUser)
        notifyDataSetChanged()
    }

    fun updateList(itemUser: List<UserModel>) {
        this.userList.addAll(itemUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.im_user.setImageURI(Uri.EMPTY)
        holder.tv_user.text = userList[position].login
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val im_user = itemView.findViewById<ImageView>(R.id.im_user)
        val tv_user = itemView.findViewById<TextView>(R.id.tv_user)

        init {
            tv_user.setOnClickListener {
                itemClick.itemClick(adapterPosition)
            }
        }
    }
}
