package com.example.android.login_api_test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.login_api_test.api.RetrofitClient
import com.example.android.login_api_test.model.LoginResponse
import com.example.android.login_api_test.model.ResultObject
import com.example.android.login_api_test.storage.SharedpreferenceManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.ProcessBuilder.Redirect.to

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        login_btn.setOnClickListener {


            val email = email_txt.text.toString().trim()
            val password = pass_txt.text.toString().trim()

            if (email.isEmpty()) {
                email_txt.error = "Email required"
                email_txt.requestFocus()
                return@setOnClickListener
            }


            if (password.isEmpty()) {
                pass_txt.error = "Password required"
                pass_txt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {


                RetrofitClient.instance.userLogin(email, password)
                    .enqueue(object : Callback<LoginResponse> {


                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message!!, Toast.LENGTH_LONG)
                                .show()

                            //debugging
                            println("Failed")
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.body()!!.result_num == 1) {

                                //SharedPreferences
                                SharedpreferenceManager.getInstance(this@MainActivity.applicationContext)
                                    .saveUser(response.body()!!.result_object[0])
                                /*Or
                                *val bundle: Bundle ?=intent.extras
                                *bundle?.putString("email", email)
                                *bundle?.putString("password", password)
                                * */

                                val intent = Intent(applicationContext, Loginsucces::class.java)

                                //  intent.putExtras(bundle!!)

                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)


                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    response.body()?.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                                println(response.body()?.toString())
                            }

                        }

                    })
            }


        }//setOnClickListener

    }//oncreate

    override fun onStart() {
        super.onStart()
        if (SharedpreferenceManager.getInstance(this).isLoggedIn == true) {

            val intent = Intent(applicationContext, Loginsucces::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }//onStart
}//class
