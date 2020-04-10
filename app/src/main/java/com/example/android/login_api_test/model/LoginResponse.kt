package com.example.android.login_api_test.model

import com.squareup.moshi.Json


//data class LoginResponse(val error: Boolean, val result_message:String)

/*data class LoginResponse(
    var result_object: List<User>,
    var result_num: Int,
    var result_message: String
)*/

/*   public List<ResultObject> result_object { get; set; }
    public int result_num { get; set; }
    public string result_message { get; set; }*/

data class LoginResponse(
    val result_message: String,
    val result_num: Int,
    val result_object: List<ResultObject>
)

data class ResultObject(
    @Json(name = "__invalid_name__.expires") val expires: String,
    @Json(name = "__invalid_name__.issued") val issued: String,
    val access_token: String,
    val balance: String,
    val countryCode: String,
    val id: String,
    val email: String,
    val expires_in: String,
    val phone: String,
    val isActive: Boolean,
    val isCustomer: Boolean,
    val isDelegate: Boolean,

    val refresh_token: String,
    val token_type: String
)