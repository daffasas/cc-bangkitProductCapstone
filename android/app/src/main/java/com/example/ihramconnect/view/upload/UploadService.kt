package com.example.ihramconnect.view.upload

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @Multipart
    @POST("upload")
    fun uploadVoiceNote(@Part file: MultipartBody.Part): Call<Void>
}