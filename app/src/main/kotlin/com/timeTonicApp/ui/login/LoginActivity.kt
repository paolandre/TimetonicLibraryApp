package com.timeTonicApp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// Modelos de respuesta de las API para Retrofit
data class AppKeyResponse(
    val status: String,
    val appkey: String
)

data class OauthKeyResponse(
    val status: String,
    val oauthkey: String
)

data class SessionKeyResponse(
    val status: String,
    val sesskey: String
)

// Retrofit API service definition
interface TimeTonicAuthService {
    @FormUrlEncoded
    @POST("/createAppkey")
    fun createAppkey(@Field("appname") appname: String): Call<AppKeyResponse>

    @FormUrlEncoded
    @POST("/createOauthkey")
    fun createOauthkey(
        @Field("login") login: String,
        @Field("pwd") pwd: String,
        @Field("appkey") appkey: String
    ): Call<OauthKeyResponse>

    @FormUrlEncoded
    @POST("/createSesskey")
    fun createSesskey(
        @Field("oauthkey") oauthkey: String
    ): Call<SessionKeyResponse>
}

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        Log.d("buttonLogin", "editTextEmail")

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            startAuthenticationProcess(email, password)
        }
    }

    private fun startAuthenticationProcess(email: String, password: String) {
        // In this method, we will start the process of authenticating the user
        // with the TimeTonic API.
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.timetonic.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TimeTonicAuthService::class.java)

        // Start with creating an app key
        service.createAppkey("api").enqueue(object : Callback<AppKeyResponse> {
            override fun onResponse(call: Call<AppKeyResponse>, response: Response<AppKeyResponse>) {
                val appKeyResponse = response.body()
                if (appKeyResponse != null && appKeyResponse.status == "ok") {
                    val appkey = appKeyResponse.appkey
                    // Now, create an oauth key
                    createOauthkey(service, email, password, appkey)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<AppKeyResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun createOauthkey(service: TimeTonicAuthService, email: String, password: String, appkey: String) {
        service.createOauthkey(email, password, appkey).enqueue(object : Callback<OauthKeyResponse> {
            override fun onResponse(call: Call<OauthKeyResponse>, response: Response<OauthKeyResponse>) {
                val oauthKeyResponse = response.body()
                if (oauthKeyResponse != null && oauthKeyResponse.status == "ok") {
                    val oauthkey = oauthKeyResponse.oauthkey
                    // Now that we have the OAuth key, we can create the session key
                    createSessionKey(service, oauthkey)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<OauthKeyResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun createSessionKey(service: TimeTonicAuthService, oauthkey: String) {
        service.createSesskey(oauthkey).enqueue(object : Callback<SessionKeyResponse> {
            override fun onResponse(call: Call<SessionKeyResponse>, response: Response<SessionKeyResponse>) {
                val sessionKeyResponse = response.body()
                if (sessionKeyResponse != null && sessionKeyResponse.status == "ok") {
                    val sessionkey = sessionKeyResponse.sesskey
                    // Save the session key and navigate to the next screen
                    saveSessionKeyAndProceed(sessionkey)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<SessionKeyResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun saveSessionKeyAndProceed(sessionkey: String) {
        val sharedPref = getSharedPreferences("TimeTonicPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("session_token", sessionkey)
            apply()
        }
        // Now navigate to the landing page
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}