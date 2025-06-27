package com.lourenc.models.domain

import java.util.UUID

data class ListItem(
    val id: UUID,
    val listId: UUID,
    val productId: UUID,
    val quantity: Int? = null
)