package com.piashcse.controller

import com.piashcse.entities.ShippingEntity
import com.piashcse.entities.ShippingTable
import com.piashcse.entities.orders.OrdersTable
import com.piashcse.entities.user.UserTable
import com.piashcse.models.shipping.AddShipping
import com.piashcse.models.shipping.UpdateShipping
import com.piashcse.utils.CommonException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class ShippingController {
    fun addShipping(userId: String, addShipping: AddShipping) = transaction {
        val isExist = ShippingEntity.find {
            UserTable.id eq userId and (OrdersTable.id eq addShipping.orderId)
        }.toList().singleOrNull()
        isExist?.let {
            throw CommonException("already exist")
        } ?: run {
            ShippingEntity.new {
                this.userId = EntityID(userId, ShippingTable)
                this.orderId = EntityID(userId, ShippingTable)
                shipAddress = addShipping.shipAddress
                shipCity = addShipping.shipCity
                shipPhone = addShipping.shipPhone
                shipName = addShipping.shipName
                shipEmail = addShipping.shipEmail

            }
        }
    }

    fun getShipping(userId: String, orderId: String) = transaction {
        val isExist = ShippingEntity.find {
            UserTable.id eq userId and (OrdersTable.id eq orderId)
        }.toList().singleOrNull()
        isExist?.response() ?: run {
            throw CommonException("Not exist")
        }
    }

    fun updateShipping(userId: String, updateShipping: UpdateShipping) = transaction {
        val isExist = ShippingEntity.find {
            UserTable.id eq userId and (OrdersTable.id eq updateShipping.orderId)
        }.toList().singleOrNull()

        isExist?.let {
            it.shipAddress = updateShipping.shipAddress ?: it.shipAddress
            it.shipCity = updateShipping.shipCity ?: it.shipCity
            it.shipPhone = updateShipping.shipPhone ?: it.shipPhone
            it.shipName = updateShipping.shipName ?: it.shipName
            it.shipEmail = updateShipping.shipEmail ?: it.shipEmail
        } ?: run {
            throw CommonException("Not exist")
        }
    }

    fun deleteShipping(userId: String, orderId: String) = transaction {
        val isExist = ShippingEntity.find {
            UserTable.id eq userId and (OrdersTable.id eq orderId)
        }.toList().singleOrNull()
        isExist?.delete() ?: run {
            throw CommonException("Not exist")
        }
    }
}