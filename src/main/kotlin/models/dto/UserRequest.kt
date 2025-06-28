package com.lourenc.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val username: String,
    val name: String,
    val password: String,
    val avatarUrl: String? = null


)