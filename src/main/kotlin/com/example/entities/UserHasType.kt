package com.example.entities

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object UserHasTypeTable : IdTable<String>("user_has_type") {
    override val id: Column<EntityID<String>> = text("user_has_type_id").uniqueIndex().entityId()
    val user_id = text("user_id").references(UsersTable.id)
    val user_type_id = text("user_type_id")
    val created_at = text("created_at")
    val updated_at = text("updated_at")
    override val primaryKey = PrimaryKey(id)
}

class UserHasTypeEntity(id: EntityID<String>) : Entity<String>(id)  {
    companion object : EntityClass<String, UserHasTypeEntity>(UserHasTypeTable)
    var id_ by UserHasTypeTable.id
    var user_id by UserHasTypeTable.user_id
    var user_type_id by UserHasTypeTable.user_type_id
    var created_at by UserHasTypeTable.created_at
    var updated_at by UserHasTypeTable.updated_at
}