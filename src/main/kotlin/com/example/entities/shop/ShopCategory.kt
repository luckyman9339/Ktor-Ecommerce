package com.example.entities.shop

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ShopCategoryTable : IdTable<String>("shop_category") {
    override val id: Column<EntityID<String>> = ShopCategoryTable.text("id").uniqueIndex().entityId()
    val shop_category_name = text("shop_category_name")
    override val primaryKey = PrimaryKey(id)
}

class ShopCategoryEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, ShopCategoryEntity>(ShopCategoryTable)

    var shopCategoryName by ShopCategoryTable.shop_category_name
    fun shopCategoryResponse() = ShopCategory(id.value, shopCategoryName)
}

data class ShopCategory(val id: String, val shopName: String)