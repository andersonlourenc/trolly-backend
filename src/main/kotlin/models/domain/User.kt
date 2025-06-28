package com.lourenc.models.domain

import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val name: String,
    val password: String,
    val avatarUrl: () -> Unit
)