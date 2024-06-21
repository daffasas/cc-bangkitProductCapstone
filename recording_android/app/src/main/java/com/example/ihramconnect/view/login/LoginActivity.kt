package com.example.ihramconnect.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.data.LoginRequest
import com.example.ihramconnect.view.home.BeforeHome
import com.example.ihramconnect.view.home.JamaahActivity
import com.example.ihramconnect.view.home.UstadActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val apiService = ApiConfig.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_login) // Perbaikan typo di layout

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.login(LoginRequest(email, password))

                    withContext(Dispatchers.Main) {
                        Log.i("LoginActivity", "Response code: ${response.code()}")
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Simpan token ke SharedPreferences
                                val role = "ustad";
                                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("auth_token", responseBody.data.token)
                                editor.putInt("user_type", responseBody.data.user.role)
                                editor.apply()
                                // Redirect sesuai dengan userType
                                when (responseBody.data.user.role) {
                                    1 -> {
                                        Toast.makeText(this@LoginActivity, "Login success as ustad", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@LoginActivity, UstadActivity::class.java))
                                    }
                                    2 -> {
                                        Toast.makeText(this@LoginActivity, "Login success as jamaah", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@LoginActivity, BeforeHome::class.java))
                                    }
                                    else -> {
                                        Toast.makeText(this@LoginActivity, "Unknown user type", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@LoginActivity, UstadActivity::class.java))
                                    }
                                }
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Login failed: Empty response body", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Response tidak berhasil (mis. 401, 404, 500, dll.)
                            val statusCode = response.code()
                            Toast.makeText(this@LoginActivity, "Login failed with status code: $statusCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Login error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
