package com.rpi.webskitterstestapplication.network

import com.rpi.webskitterstestapplication.common.AppConstants
import com.rpi.webskitterstestapplication.model.SingleUserResponseModel
import com.rpi.webskitterstestapplication.model.UserListResponseModel
import com.rpi.webskitterstestapplication.model.UserLoginRequestModel
import com.rpi.webskitterstestapplication.model.UserLoginResponseModel
import retrofit2.Call
import retrofit2.http.*

interface ReqResAPIService {

    /**
     * Login API Service
     */
    @Headers(AppConstants.HEADER_APPLICATION_JSON)
    @POST(AppConstants.REQUEST_TYPE_LOGIN)
    fun apiLoginRequest(@Body userLoginRequestModel: UserLoginRequestModel): Call<UserLoginResponseModel>

    /**
     * User List API Service
     */
    @Headers(AppConstants.HEADER_APPLICATION_JSON)
    @GET(AppConstants.REQUEST_TYPE_USER_LIST)
    fun apiUserListRequest(@Query("page") pageNo: Int): Call<UserListResponseModel>

    /**
     * User Details API Service
     */
    @Headers(AppConstants.HEADER_APPLICATION_JSON)
    @GET(AppConstants.REQUEST_TYPE_SINGLE_USER)
    fun apiUserDetailsRequest(@Path("id") id: Int?): Call<SingleUserResponseModel>
}