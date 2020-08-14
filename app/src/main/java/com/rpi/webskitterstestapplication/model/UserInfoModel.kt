package com.rpi.webskitterstestapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoModel (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("first_name")
    val first_name: String,
    @field:SerializedName("last_name")
    val last_name: String,
    @field:SerializedName("avatar")
    val avatar: String
) : Parcelable