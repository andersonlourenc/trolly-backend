package com.lourenc.models.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserResponse(
    @Contextual val id: UUID,
    val username: String,
    val name: String,
    val email: String
)
