package com.lourenc.presentation.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val name: String,
    val password: String,
    val avatarUrl: String? = null


)