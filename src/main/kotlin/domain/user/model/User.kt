package com.lourenc.domain.user.model

import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val name: String,
    val password: String,
    val avatarUrl: String? = null,
    val createdAt: LocalDateTime? = null
)