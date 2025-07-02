package com.lourenc.presentation.user.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class UserResponse(
    @Contextual val id: UUID,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val createdAt: String?
)