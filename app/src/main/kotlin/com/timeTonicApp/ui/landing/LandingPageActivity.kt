package com.timeTonicApp.ui.landing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.timeTonicApp.R
import com.timeTonicApp.ui.login.LoginActivity

class LandingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val buttonLogOut: Button = findViewById(R.id.buttonLogOut)

        buttonLogOut.setOnClickListener {
            navigateToLogInPage()
        }
    }

    private fun navigateToLogInPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
