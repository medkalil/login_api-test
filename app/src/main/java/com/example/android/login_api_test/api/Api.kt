package com.example.android.login_api_test.api

import com.example.android.login_api_test.model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun userLogin(
        @Field( "username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

}
