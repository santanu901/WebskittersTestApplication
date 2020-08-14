package com.rpi.webskitterstestapplication.network

import com.rpi.webskitterstestapplication.common.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReqResAPIClient {

    val reqResAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.ReqRes_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReqResAPIService::class.java)
    }
}