package com.lourenc.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val email: String? = null,
    val username: String? = null,
    val name: String? = null,
    val password: String? = null,
    val avatarUrl: String? = null,
)