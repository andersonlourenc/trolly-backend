package com.lourenc.repository

import com.lourenc.models.domain.Product
import com.lourenc.models.dto.ProductRequest
import com.lourenc.models.dto.ProductResponse
import com.lourenc.models.tables.ProductsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object ProductRepository {

    fun getById(id: UUID): ProductResponse? {
        return transaction {
            ProductsTable.select { ProductsTable.id eq id }
                .mapNotNull { row ->
                    ProductResponse(
                        id = row[ProductsTable.id].value,
                        name = row[ProductsTable.name],
                        category = row[ProductsTable.category] ?:"",
                        unit = row[ProductsTable.unit],
                        imageUrl = row[ProductsTable.imageUrl]
                    )
                }
                .singleOrNull()
        }
    }


    fun update(id: UUID, request: ProductRequest): ProductResponse = transaction {
        ProductsTable.update({ ProductsTable.id eq id }) {
            it[name] = request.name
            it[category] = request.category
            it[unit] = request.unit
            it[imageUrl] = request.imageUrl
        }

        val row = ProductsTable.select { ProductsTable.id eq id }.single()

        ProductResponse(
            id = row[ProductsTable.id].value,
            name = row[ProductsTable.name],
            category = request.category,
            unit = request.unit,
            imageUrl = row[ProductsTable.imageUrl]
        )
        getById(id) ?: throw NoSuchElementException("Product with id $id not found")
    }



    fun createOrGet(product: ProductRequest): ProductResponse = transaction {
        val existing = ProductsTable.select {
            (ProductsTable.name eq product.name) and
                    (ProductsTable.category eq product.category) and
                    (ProductsTable.unit eq product.unit)
        }.singleOrNull()

        val row = existing ?: ProductsTable.insert {
            it[name] = product.name
            it[category] = product.category
            it[unit] = product.unit
            it[imageUrl] = product.imageUrl
        }.resultedValues!!.first()

        ProductResponse(
            id = row[ProductsTable.id].value,
            name = row[ProductsTable.name],
            category = row[ProductsTable.category] ?: "",
            unit = row[ProductsTable.unit],
            imageUrl = row[ProductsTable.imageUrl]
        )
    }


    fun insert(product: ProductRequest): UUID {

        val id = UUID.randomUUID()

        transaction {
            ProductsTable.insert {
                it[ProductsTable.id] = id
                it[name] = product.name
                it[category] = product.category
                it[unit] = product.unit
                it[imageUrl] = product.imageUrl

            }
        }
        return id
    }


    fun getAll(
        name: String? = null,
        category: String? = null,
        unit: String? = null
    ): List<ProductResponse> {
        return transaction {
            var query = ProductsTable.selectAll()

            if (!name.isNullOrBlank()) {
                query = query.andWhere { ProductsTable.name like "%$name%" }
            }
            if (!category.isNullOrBlank()) {
                query = query.andWhere { ProductsTable.category eq category }
            }
            if (!unit.isNullOrBlank()) {
                query = query.andWhere { ProductsTable.unit eq unit }
            }

            query.map {
                ProductResponse(
                    id = it[ProductsTable.id].value,
                    name = it[ProductsTable.name],
                    category = it[ProductsTable.category] ?: "",
                    unit = it[ProductsTable.unit],
                    imageUrl = it[ProductsTable.imageUrl]
                )
            }
        }
    }
}

