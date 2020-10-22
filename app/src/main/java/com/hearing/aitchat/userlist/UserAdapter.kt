package com.hearing.aitchat.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearing.aitchat.R
import com.hearing.aitchat.edit.User

/**
 * @author liujiadong
 * @since 2020/10/21
 */
class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {
    private var users = mutableListOf<User>()
    var onUserChooseListener: OnUserChooseListener? = null

    fun setData(data: List<User>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val holder = UserHolder.create(parent)
        holder.onUserChooseListener = onUserChooseListener
        return holder
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        if (position >= 0 && position < users.size) {
            holder.fillData(users[position])
        }
    }

    interface OnUserChooseListener {
        fun select(user: User)
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): UserHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
                return UserHolder(view)
            }
        }

        private val userNameTv = itemView.findViewById<TextView>(R.id.user_name_tv)
        var onUserChooseListener: OnUserChooseListener? = null

        fun fillData(user: User) {
            itemView.setOnClickListener {
                onUserChooseListener?.select(user)
            }
            userNameTv.text = user.name
        }
    }
}