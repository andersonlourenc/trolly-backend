package com.lourenc.domain.product.usecase

import com.lourenc.domain.product.repository.ProductRepository
import com.lourenc.presentation.product.dto.ProductRequest
import com.lourenc.presentation.product.dto.ProductResponse

object ProductService {
    fun getAll(
        name: String? = null,
        category: String? = null,
        unit: String? = null,
    ): List<ProductResponse> = ProductRepository.getAll(name, category, unit)

    fun createOrGetProduct(productRequest: ProductRequest): ProductResponse =
        ProductRepository.createOrGet(productRequest)
}