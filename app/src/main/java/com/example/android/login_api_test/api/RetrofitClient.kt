package com.example.android.login_api_test.api

import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString
import com.example.android.login_api_test.model.LoginResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


private val AUTH = "Basic" + encodeToString("555006187:555006187".toByteArray(), NO_WRAP)

private const val BASE_URL = "https://camion-ksa.com/devenv/"

//create the okHttpClient soo wwe can integratet in the retrofit wen we created
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(100, TimeUnit.SECONDS)
    .addInterceptor { chain ->
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", AUTH)
            .method(original.method(), original.body())

        val request = requestBuilder.build()
        chain.proceed(request)
    }.build()


val gson = GsonBuilder()
    .setLenient()
    .setPrettyPrinting()
    .create()


//moshi => used to parse the json into a kotlin obj
val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    //.addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

object RetrofitClient {
    val instance: Api by lazy {

        retrofit.create(Api::class.java)

    }

}