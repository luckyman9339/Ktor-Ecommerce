package com.example.entities.orders

import com.example.entities.base.BaseIntEntity
import com.example.entities.base.BaseIntEntityClass
import com.example.entities.base.BaseIntIdTable
import com.example.entities.user.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object OrdersTable : BaseIntIdTable("orders") {
    val user_id = reference("user_id", UserTable.id)
    val order_amount = float("order_amount")
    val shipping_address_1 = text("shipping_address_1")
    val shipping_address_2 = text("shipping_address_2")
    val city = text("city")
    val order_email = text("order_email")
    val order_date = datetime("order_date")
    val order_tracking_number = text("order_tracking_number")
}

class OrdersEntity(id: EntityID<String>) : BaseIntEntity(id, OrdersTable) {
    companion object : BaseIntEntityClass<OrdersEntity>(OrdersTable)

    var user_id by OrdersTable.user_id
    var order_amount by OrdersTable.order_amount
    var shipping_address_1 by OrdersTable.shipping_address_1
    var shipping_address_2 by OrdersTable.shipping_address_2
    var city by OrdersTable.city
    var order_email by OrdersTable.order_email
    var order_date by OrdersTable.order_date
    var order_tracking_number by OrdersTable.order_tracking_number
    // fun shopResponse() = Shop(id.value, shop_name)
}