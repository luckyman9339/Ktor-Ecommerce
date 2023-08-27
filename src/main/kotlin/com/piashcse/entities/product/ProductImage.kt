package com.piashcse.entities.product

import com.piashcse.entities.base.BaseIntEntity
import com.piashcse.entities.base.BaseIntEntityClass
import com.piashcse.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ProductImage : BaseIntIdTable("product_image") {
    val userId = reference("user_id", id)
    val productId = reference("product_id", ProductTable.id)
    val imageUrl = text("image_url") // multiple image will be saved comma seperated string
}

class ProductImageEntity(id: EntityID<String>) : BaseIntEntity(id, ProductImage) {
    companion object : BaseIntEntityClass<ProductImageEntity>(ProductImage)

    var userId by ProductImage.userId
    var productId by ProductImage.productId
    var imageUrl by ProductImage.imageUrl
    fun response() = ImageUrl(id.value, imageUrl)
}

data class ImageUrl(
    val id: String,
    val imageUrl: String,
)