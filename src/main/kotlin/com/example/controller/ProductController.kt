package com.example.controller

import com.example.entities.product.*
import com.example.entities.product.defaultvariant.ProductColorEntity
import com.example.entities.product.defaultvariant.ProductColorTable
import com.example.entities.product.defaultvariant.ProductSizeEntity
import com.example.entities.product.defaultvariant.ProductSizeTable
import com.example.utils.CommonException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductController {
    fun createProductCategory(productCategoryName: String) = transaction {
        val categoryExist =
            ProductCategoryEntity.find { ProductCategoryTable.product_category_name eq productCategoryName }.toList()
                .singleOrNull()
        return@transaction if (categoryExist == null) {
            ProductCategoryEntity.new(UUID.randomUUID().toString()) {
                product_category_name = productCategoryName
            }.productCategoryResponse()
        } else {
            throw CommonException("Product category name $productCategoryName already exist")
        }
    }

    /*    fun createVariant(colorName: String) = transaction {
            val colorExist = ProductVariantEntity.find { ProductVariantTable.name eq colorName }.toList().singleOrNull()
            return@transaction if (colorExist == null) {
                ProductVariantEntity.new(UUID.randomUUID().toString()) {
                    name = colorName
                }.response()
            } else {
                throw CommonException("Color name $colorName already exist")
            }
        }*/
    fun createDefaultColorOption(colorName: String) = transaction {
        val colorExist = ProductColorEntity.find { ProductColorTable.name eq colorName }.toList().singleOrNull()
        return@transaction if (colorExist == null) {
            ProductColorEntity.new(UUID.randomUUID().toString()) {
                name = colorName
            }.response()
        } else {
            throw CommonException("Color name $colorName already exist")
        }
    }

    fun createDefaultSizeOption(sizeName: String) = transaction {
        val sizeExist = ProductSizeEntity.find { ProductSizeTable.name eq sizeName }.toList().singleOrNull()
        return@transaction if (sizeExist == null) {
            ProductSizeEntity.new(UUID.randomUUID().toString()) {
                name = sizeName
            }.response()
        } else {
            throw CommonException("Size name $sizeName already exist")
        }
    }

    fun createProduct() {
        ProductEntity.new(UUID.randomUUID().toString()) {

        }
    }
}