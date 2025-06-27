package com.lourenc.models.domain

import com.lourenc.serialization.UUIDSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @Serializable(with = UUIDSerializer::class)

    val name: String,
    val category: String? = null,
    val unit: String

)