package com.example.android.login_api_test.storage

import android.content.Context
import com.example.android.login_api_test.model.ResultObject

class SharedpreferenceManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: ResultObject
        get() {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return ResultObject(
                sharedPreferences.getString("expires", null)!!,
                sharedPreferences.getString("issued", null)!!,
                sharedPreferences.getString("access_token", null)!!,
                sharedPreferences.getString("balance", null)!!,
                sharedPreferences.getString("countryCode", null)!!,
                sharedPreferences.getString("id", null)!!,
                sharedPreferences.getString("email", null)!!,
                sharedPreferences.getString("expires_in", null)!!,
                sharedPreferences.getString("phone", null)!!,
                sharedPreferences.getBoolean("isActive", false),
                sharedPreferences.getBoolean("isCustomer", false),
                sharedPreferences.getBoolean("isDelegate", false),
                sharedPreferences.getString("refresh_token", null)!!,
                sharedPreferences.getString("token_type", null)!!
                )
        }


    fun saveUser(user: ResultObject) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("id", user.id)
        editor.putString("email", user.email)
        editor.putString("name", user.phone)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedpreferenceManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedpreferenceManager {
            if (mInstance == null) {
                mInstance = SharedpreferenceManager(mCtx)
            }
            return mInstance as SharedpreferenceManager
        }
    }

}