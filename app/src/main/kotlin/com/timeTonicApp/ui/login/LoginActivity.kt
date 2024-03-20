package com.timeTonicApp.ui.login

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timeTonicApp.data.model.*
import com.timeTonicApp.data.remote.RetrofitInstance
import com.timeTonicApp.ui.landing.LandingPageActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
                Log.d("LoginActivity", "Intentando inicio de sesión con email: $email")
            } else {
                Toast.makeText(this, R.string.login_empty_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val authService = RetrofitInstance.api
        authService.createAppKey("api").enqueue(object : Callback<AppKeyResponse> {
            override fun onResponse(
                call: Call<AppKeyResponse>,
                response: Response<AppKeyResponse>
            ) {
                if (response.isSuccessful) {
                    val appKey = response.body()?.appkey
                    appKey?.let {
                        getOAuthKey(email, password, it)
                        Log.d("appkey", "appKey: $appKey")
                    } ?: run {
                        showError("App Key retrieval failed")
                    }
                } else {
                    showError("App Key retrieval failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<AppKeyResponse>, t: Throwable) {
                showError("Network error: ${t.message}")
            }
        })
    }

    private fun getOAuthKey(email: String, password: String, appKey: String) {
        val authService = RetrofitInstance.api
        authService.createOAuthKey(email, password, appKey)
            .enqueue(object : Callback<OauthKeyResponse> {
                override fun onResponse(
                    call: Call<OauthKeyResponse>,
                    response: Response<OauthKeyResponse>
                ) {
                    if (response.isSuccessful) {
                        val oauthKey = response.body()?.oauthkey
                        oauthKey?.let {
                            getSessionKey(it)
                        } ?: run {
                            showError("OAuth Key retrieval failed")
                        }
                    } else {
                        showError("OAuth Key retrieval failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<OauthKeyResponse>, t: Throwable) {
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun getSessionKey(oauthKey: String) {
        val authService = RetrofitInstance.api
        authService.createSessionKey("6.49q/6.49", "createSesskey", "demo", "demo", oauthKey)
            .enqueue(object : Callback<SessionKeyResponse> {
                override fun onResponse(
                    call: Call<SessionKeyResponse>,
                    response: Response<SessionKeyResponse>
                ) {
                    if (response.isSuccessful) {
                        val sessionKey = response.body()?.sesskey
                        sessionKey?.let {
                            saveSessionKey(it)
                            navigateToLandingPage()
                        } ?: run {
                            showError("Session Key retrieval failed")
                        }
                    } else {
                        showError("Session Key retrieval failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SessionKeyResponse>, t: Throwable) {
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun saveSessionKey(sessionKey: String) {
        val sharedPreferences = getSharedPreferences("TimeTonicPrefs", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("session_key", sessionKey)
            apply()
        }
    }

    private fun navigateToLandingPage() {
        val intent = Intent(this, LandingPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }
}