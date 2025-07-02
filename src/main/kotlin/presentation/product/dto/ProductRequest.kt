package com.lourenc.presentation.product.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    val name: String,
    val category: String,
    val unit: String,
    val imageUrl: String? = null,
)