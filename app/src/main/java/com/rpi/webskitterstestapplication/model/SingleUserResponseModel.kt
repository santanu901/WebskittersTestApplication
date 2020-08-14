package com.rpi.webskitterstestapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SingleUserResponseModel (
    @field:SerializedName("data")
    val userInfoModel: UserInfoModel
) : Parcelable