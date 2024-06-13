package com.example.ihramconnect.model

data class user(
    val email: String,
    var password: String,
    val userType: UserType
){
    fun isAdmin(): Boolean {
        return userType == UserType.ADMIN
    }
}
