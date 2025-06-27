package com.lourenc.application

import com.lourenc.repository.ProductRepository
import com.lourenc.models.domain.Product
import com.lourenc.models.dto.ProductRequest
import com.lourenc.models.dto.ProductResponse
import com.lourenc.models.tables.ProductsTable
import java.util.UUID

object ProductService {
    fun getAll(
        name: String? = null,
        category: String? = null,
        unit: String? = null,
    ): List<ProductResponse> = ProductRepository.getAll(name, category, unit)

    fun createOrGetProduct(productRequest: ProductRequest): ProductResponse =
        ProductRepository.createOrGet(productRequest)
}