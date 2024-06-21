package com.example.ihramconnect.view.updatePW

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiService
import com.example.ihramconnect.data.ChangePasswordRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        apiService = ApiService.create()

        val oldPasswordEditText = findViewById<EditText>(R.id.oldPasswordEditText)
        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val response = apiService.changePassword(ChangePasswordRequest("user@example.com", oldPassword, newPassword))
                withContext(Dispatchers.Main) {
                    if (response.success) {
                        Toast.makeText(this@ChangePasswordActivity, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ChangePasswordActivity, "Password change failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}