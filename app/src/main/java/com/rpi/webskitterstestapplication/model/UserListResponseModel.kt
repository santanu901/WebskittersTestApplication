package com.rpi.webskitterstestapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListResponseModel (
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("per_page")
    val per_page: Int,
    @field:SerializedName("total")
    val total: String,
    @field:SerializedName("total_pages")
    val total_pages: Int,
    @field:SerializedName("data")
    val userList: List<UserInfoModel>
) : Parcelable