package com.lourenc.presentation.product.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProductResponse(
    @Contextual val id: UUID,
    val name: String,
    val category: String,
    val unit: String,
    val imageUrl: String? = null
)