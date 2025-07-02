package com.lourenc.domain.product.model

import com.lourenc.utils.UUIDSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @Serializable(with = UUIDSerializer::class)

    val name: String,
    val category: String? = null,
    val unit: String

)