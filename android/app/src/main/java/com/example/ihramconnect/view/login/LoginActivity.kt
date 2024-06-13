package com.example.ihramconnect.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.model.UserType
import com.example.ihramconnect.model.user
import com.example.ihramconnect.view.home.AdminHomeActivity
import com.example.ihramconnect.view.home.JamaahHomeActivity
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Panggil API login
            val apiService = ApiConfig.getApiService()
            val call = apiService.login(email, password)
            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val user = response.body()?.user
                        user?.let {
                            if (it.isAdmin()) {
                                startActivity(Intent(this@LoginActivity, AdminHomeActivity::class.java))
                            } else {
                                startActivity(Intent(this@LoginActivity, JamaahHomeActivity::class.java))
                            }
                            finish()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}