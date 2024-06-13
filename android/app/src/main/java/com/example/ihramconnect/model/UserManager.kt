package com.example.ihramconnect.model

import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.view.login.LoginResponse
import com.example.ihramconnect.view.updatePW.UpdatePasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserManager {
    private val apiService = ApiConfig.getApiService()

    fun getUserByEmail(email: String, callback: (user?) -> Unit) {
        val call = apiService.login(email, "") // Gunakan email untuk mendapatkan user
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    callback(response.body()?.user)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun updateUserPassword(email: String, newPassword: String, callback: (Boolean, String) -> Unit) {
        val call = apiService.updatePassword(email, newPassword)
        call.enqueue(object : Callback<UpdatePasswordResponse> {
            override fun onResponse(call: Call<UpdatePasswordResponse>, response: Response<UpdatePasswordResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    callback(true, response.body()?.message ?: "Password updated successfully")
                } else {
                    callback(false, response.body()?.message ?: "Failed to update password")
                }
            }

            override fun onFailure(call: Call<UpdatePasswordResponse>, t: Throwable) {
                callback(false, t.message ?: "Unknown error")
            }
        })
    }
}