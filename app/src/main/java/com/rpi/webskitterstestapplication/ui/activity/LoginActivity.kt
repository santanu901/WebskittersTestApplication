package com.rpi.webskitterstestapplication.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.property.app.sharedpref.SharedPrefDetails
import com.rpi.webskitterstestapplication.utils.NetworkUtility
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.model.UserLoginRequestModel
import com.rpi.webskitterstestapplication.model.UserLoginResponseModel
import com.rpi.webskitterstestapplication.network.ReqResAPIClient
import com.rpi.webskitterstestapplication.network.ReqResAPIService
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var loadingDialog: AlertDialog
    private lateinit var reqResAPIService: ReqResAPIService
    private lateinit var sharedPref: SharedPrefDetails

    private var inputEmail: String = ""
    private var inputPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initVariable()
        initListeners()
    }

    private fun initVariable() {
        sharedPref = SharedPrefDetails(this)
        reqResAPIService = ReqResAPIClient.reqResAPIService
        loadingDialog = SpotsDialog.Builder()
            .setContext(this)
            .setTheme(R.style.CustomLoadingDialog)
            .build()
    }

    private fun initListeners() {
        btn_login.setOnClickListener(this)
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

    private fun validateLoginInputFields(): Boolean {
        inputEmail = et_email.text.toString().trim()
        inputPassword = et_password.text.toString().trim()

        if (inputEmail.isEmpty()) {
            Snackbar.make(cl_loginRoot, "Please Enter Email Id", Snackbar.LENGTH_SHORT).show()
            return false
        } else if (inputPassword.isEmpty()) {
            Snackbar.make(cl_loginRoot, "Please Enter Password", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun initLoginService() {
        if (NetworkUtility.isOnline(this@LoginActivity)) {
            if (validateLoginInputFields()) {
                showProgressDialog()
                callLoginService()
            }
        } else {
            Snackbar.make(
                cl_loginRoot,
                "Please Check Your Internet Connection",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun callLoginService() {
        val userLoginRequestModel = UserLoginRequestModel(inputEmail, inputPassword)
        val loginCallback = reqResAPIService.apiLoginRequest(userLoginRequestModel)
        loginCallback.enqueue(object : Callback<UserLoginResponseModel> {
            override fun onFailure(call: Call<UserLoginResponseModel>, t: Throwable) {
                dismissProgressDialog()
                Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<UserLoginResponseModel>,
                response: Response<UserLoginResponseModel>
            ) {
                dismissProgressDialog()
                if (response.code() == 200) {
                    sharedPref.setUserLoginToken(response.body()?.token)

                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> initLoginService()
        }
    }
}