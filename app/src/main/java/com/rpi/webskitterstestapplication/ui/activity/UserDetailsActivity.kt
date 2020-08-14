package com.rpi.webskitterstestapplication.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.model.SingleUserResponseModel
import com.rpi.webskitterstestapplication.model.UserLoginRequestModel
import com.rpi.webskitterstestapplication.model.UserLoginResponseModel
import com.rpi.webskitterstestapplication.network.ReqResAPIClient
import com.rpi.webskitterstestapplication.network.ReqResAPIService
import com.rpi.webskitterstestapplication.utils.NetworkUtility
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.toolbar_common.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var loadingDialog: AlertDialog
    private lateinit var reqResAPIService: ReqResAPIService
    private var userId: Int? = 0
    private var resultIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        initVariable()
        initToolbar()

        initUserDetailsService()
    }

    private fun initVariable() {
        resultIntent = intent
        resultIntent?.let {
            userId = it.getIntExtra(INTENT_KEY_USER_ID, 0)
        }

        reqResAPIService = ReqResAPIClient.reqResAPIService
        loadingDialog = SpotsDialog.Builder()
            .setContext(this)
            .setTheme(R.style.CustomLoadingDialog)
            .build()
    }

    private fun initToolbar() {
        toolbar_title.text = "User Details"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun navigateBack() {
        finish()
    }

    private fun showProgressDialog() {
        if (this::loadingDialog.isInitialized && loadingDialog.isShowing.not()) {
            loadingDialog.show()
        }
    }

    private fun dismissProgressDialog() {
        if (this::loadingDialog.isInitialized && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    private fun initUserDetailsService() {
        if (NetworkUtility.isOnline(this@UserDetailsActivity)) {
            userId?.let {id->
                if (id != 0) {
                    showProgressDialog()
                    callUserDetailsService()
                }
            }
        } else {
            Snackbar.make(
                cl_loginRoot,
                "Please Check Your Internet Connection",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun callUserDetailsService() {
        val userDetailsCallback = reqResAPIService.apiUserDetailsRequest(userId)
        userDetailsCallback.enqueue(object : Callback<SingleUserResponseModel> {
            override fun onFailure(call: Call<SingleUserResponseModel>, t: Throwable) {
                dismissProgressDialog()
                Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<SingleUserResponseModel>,
                response: Response<SingleUserResponseModel>
            ) {
                dismissProgressDialog()
                if (response.code() == 200) {
                   val userInfoModel = response.body()?.userInfoModel
                    userInfoModel?.let {userInfo ->
                        Picasso.with(this@UserDetailsActivity)
                            .load(userInfo.avatar)
                            .placeholder(R.drawable.ic_profile_placeholder)
                            .error(R.drawable.ic_profile_placeholder)
                            .into(iv_profileImage)

                        tv_FirstName.text = userInfo.first_name
                        tv_LastName.text = userInfo.last_name
                        tv_email.text = userInfo.email
                    }
                } else {
                    Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    companion object {
        const val INTENT_KEY_USER_ID = "KEY_USER_ID"
    }
}