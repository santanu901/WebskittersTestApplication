package com.rpi.webskitterstestapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.model.UserInfoModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(
    private val context: Context?,
    private val userList: List<UserInfoModel>,
    private val userRowClickListener: UserRowClickListener
) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    private var mContext: Context? = null
    private var mUserRowClickListener: UserRowClickListener? = null

    init {
        mContext = context
        mUserRowClickListener = userRowClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserListViewHolder {
        val inflatedView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_user_list_item, viewGroup, false)
        return UserListViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userInfoModel: UserInfoModel? = userList[position]

        userInfoModel?.let { userInfo ->
            Picasso.with(context)
                .load(userInfo.avatar)
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(holder.ivUser)
            holder.tvUserName.text = "${userInfo.first_name} ${userInfo.last_name}"
            holder.tvEmail.text = userInfo.email

            holder.rlUserRowRoot.setOnClickListener {
                mUserRowClickListener?.onUserRowClicked(userInfo)
            }
        }
    }

    interface UserRowClickListener {
        fun onUserRowClicked(userInfoModel: UserInfoModel?)
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rlUserRowRoot = itemView.findViewById(R.id.rl_user_row_root) as RelativeLayout
        var ivUser = itemView.findViewById(R.id.iv_user) as CircleImageView
        var tvUserName = itemView.findViewById(R.id.tv_user_name) as TextView
        var tvEmail = itemView.findViewById(R.id.tv_email) as TextView
    }
}