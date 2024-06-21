package com.example.ihramconnect.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("changePassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseResponse

    @Multipart
    @POST("/voices")
    suspend fun uploadRecording(
        @Header("Authorization") authToken: String,
        @Part("title") title: String,
        @Part file: MultipartBody.Part
    ): Response<ApiResponse>

    @GET("/voices")
    suspend fun getRecordings(
        @Header("Authorization") authToken: String,
    ): List<Recording>


    @GET("/places/")
    suspend fun getPlaces(
        @Header("Authorization") authToken: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): PlaceResponse


    @DELETE("/voices/{id}")
    suspend fun deleteRecording(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int): Response<DeleteResponse>

    companion object {
        private const val BASE_URL = "https://ihram-connect-api-zsawvcuetq-et.a.run.app"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}


data class LoginResponse(
    val message: String,
    val data: Data
)

data class Data(
    val user: User,
    val token: String
)

data class User(
    val email: String,
    val role: Int
)

data class LoginRequest(val email: String, val password: String)
data class DeleteResponse(val message:String);
data class ChangePasswordRequest(val email: String, val oldPassword: String, val newPassword: String)
data class BaseResponse(val success: Boolean)
data class Recording(val id: Int, val title: String, val url: String , val filePath:String)
data class Recomendation(val id: Int, val title: String, val url: String , val description : String )


data class PlaceResponse(
    val data: List<Place>,
    val currentPage: Int,
    val totalPages: Int,
    val totalPlaces: Int
)

data class Place(
    val id: Int,
    val place: String,
    val alamat: String,
    val rating: Double,
    val jarakMekkah: Double
)



data class ApiResponse(
    val success: Boolean,
    val message: String
)