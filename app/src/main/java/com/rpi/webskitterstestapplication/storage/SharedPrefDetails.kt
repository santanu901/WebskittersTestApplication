package com.property.app.sharedpref

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by AB on 25/02/2020.
 */

class SharedPrefDetails// Constructor
    (private val mContext: Context) {

    private val userPref: SharedPreferences
    private val userEditor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0

    private val PREF_NAME = "user_details"

    init {
        userPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        userEditor = userPref.edit()
    }

    val userToken: String?
        get() = userPref.getString(userLoginToken, "")

    fun setUserLoginToken(token: String?) {
        userEditor.putString(userLoginToken, token)
        userEditor.commit()
    }

    // Set Clear
    fun setClear() {
        userEditor.clear()
        userEditor.commit()
    }

    companion object {
        var userLoginToken = "USER_LOGIN_TOKEN"
        var isLoggedIn = "isLoggedIn"
        internal var KEY_User_DETAILS = "KEY_User_DETAILS"
        internal var KEY_Access_Token = "KEY_Access_Token"
        var is1stTime = "is1stTime"
        internal var selectLang = "selectLang"
        var isLoggedout = "isLoggedout"

    }

}
