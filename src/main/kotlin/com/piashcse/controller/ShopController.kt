package com.piashcse.controller

import com.piashcse.entities.shop.*
import com.piashcse.entities.user.UserTable
import com.piashcse.utils.CommonException
import com.piashcse.utils.extension.alreadyExistException
import com.piashcse.utils.extension.isNotExistException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class ShopController {
    fun createShopCategory(shopCategoryName: String) = transaction {
        val categoryExist =
            ShopCategoryEntity.find { ShopCategoryTable.shopCategoryName eq shopCategoryName }.toList().singleOrNull()
        if (categoryExist == null) {
            ShopCategoryEntity.new {
                this.shopCategoryName = shopCategoryName
            }.shopCategoryResponse()
        } else {
            shopCategoryName.alreadyExistException()
        }
    }

    fun getShopCategories(limit: Int, offset: Long) = transaction {
        val shopCategories = ShopCategoryEntity.all().limit(limit, offset)
        shopCategories.map {
            it.shopCategoryResponse()
        }
    }

    fun updateShopCategory(shopCategoryId: String, shopCategoryName: String) = transaction {
        val shopCategoryExist =
            ShopCategoryEntity.find { ShopCategoryTable.id eq shopCategoryId }.toList().singleOrNull()
        shopCategoryExist?.apply {
            this.shopCategoryName = shopCategoryName
        }?.shopCategoryResponse() ?: shopCategoryId.isNotExistException()
    }

    fun deleteShopCategory(shopCategoryId: String) = transaction {
        val shopCategoryExist =
            ShopCategoryEntity.find { ShopCategoryTable.id eq shopCategoryId }.toList().singleOrNull()
        shopCategoryExist?.let {
            shopCategoryExist.delete()
            shopCategoryId
        } ?: run {
            shopCategoryId.isNotExistException()
        }
    }

    fun createShop(userId: String, shopCategoryId: String, shopName: String) = transaction {
        val shopNameExist = ShopEntity.find { ShopTable.shopName eq shopName }.toList().singleOrNull()
        if (shopNameExist == null) {
            ShopEntity.new {
                this.userId = EntityID(userId, UserTable)
                this.shopCategoryId = EntityID(shopCategoryId, ShopTable)
                this.shopName = shopName
            }.shopResponse()
        } else {
            shopName.alreadyExistException()
        }
    }
}