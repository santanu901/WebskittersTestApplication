package com.rpi.webskitterstestapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginRequestModel (
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String
) : Parcelable