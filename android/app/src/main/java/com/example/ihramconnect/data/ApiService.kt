package com.example.ihramconnect.data

import com.example.ihramconnect.view.login.LoginResponse
import com.example.ihramconnect.view.updatePW.UpdatePasswordResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("updatePassword")
    @FormUrlEncoded
    fun updatePassword(
        @Field("email") email: String,
        @Field("newPassword") newPassword: String
    ): Call<UpdatePasswordResponse>
}