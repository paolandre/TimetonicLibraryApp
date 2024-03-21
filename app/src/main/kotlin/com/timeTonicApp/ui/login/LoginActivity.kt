package com.timeTonicApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.timeTonicApp.R
import com.timeTonicApp.data.model.AppKeyResponse
import com.timeTonicApp.data.model.AuthKeyResponse
import com.timeTonicApp.data.model.CreateSessKeyResponse
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
            } else {
                Toast.makeText(this, R.string.login_empty_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val authService = RetrofitInstance.api
        authService.createAppKey(req = "createAppkey", appName = "TimeTonicApp").enqueue(object : Callback<AppKeyResponse> {
            override fun onResponse(call: Call<AppKeyResponse>, response: Response<AppKeyResponse>) {
                if (response.isSuccessful) {
                    response.body()?.appkey?.let { appKey ->
                        callCreateOauthkey(email, password, appKey)
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

    private fun callCreateOauthkey(email: String, password: String, appKey: String) {
        val authService = RetrofitInstance.api
        authService.createOauthkey(
            req = "createOauthkey",
            login = email,
            pwd = password,
            appkey = appKey
        ).enqueue(object : Callback<AuthKeyResponse> {
            override fun onResponse(call: Call<AuthKeyResponse>, response: Response<AuthKeyResponse>) {
                if (response.isSuccessful) {
                    response.body()?.oauthkey?.let { oauthKey ->
                        callCreateSesskey(oauthKey, response.body()?.o_u ?: "")
                    } ?: run {
                        showError("OAuth Key retrieval failed")
                    }
                } else {
                    showError("OAuth Key retrieval failed: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<AuthKeyResponse>, t: Throwable) {
                showError("Network error: ${t.message}")
            }
        })
    }

    private fun callCreateSesskey(oauthkey: String, o_u: String) {
        val authService = RetrofitInstance.api
        authService.createSesskey(
            req = "createSesskey",
            o_u = o_u,
            oauthkey = oauthkey
        ).enqueue(object : Callback<CreateSessKeyResponse> {
            override fun onResponse(
                call: Call<CreateSessKeyResponse>,
                response: Response<CreateSessKeyResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.sesskey?.let {
                        navigateToLandingPage()
                    } ?: run {
                        showError("Session Key retrieval failed")
                    }
                } else {
                    showError("Session Key retrieval failed: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<CreateSessKeyResponse>, t: Throwable) {
                showError("Network error: ${t.message}")
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, LENGTH_LONG).show()
    }

    private fun navigateToLandingPage() {
        val intent = Intent(this, LandingPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
