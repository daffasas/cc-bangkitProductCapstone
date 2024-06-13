package com.example.ihramconnect.view.login

import com.example.ihramconnect.model.user

data class LoginResponse(
    val success: Boolean,
    val user: user?
)
