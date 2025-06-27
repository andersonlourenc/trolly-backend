package com.lourenc.models.dto

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProductRequest(
    val name: String,
    val category: String,
    val unit: String,
    val imageUrl: String? = null,
)
