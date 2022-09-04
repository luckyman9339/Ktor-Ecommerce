package com.example.entities.user

import com.example.entities.base.BaseIntEntity
import com.example.entities.base.BaseIntEntityClass
import com.example.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object UserProfileTable : BaseIntIdTable("users_profile") {
    val user_id = reference("user_id", UserTable.id)
    val user_profile_image = text("user_profile_image").nullable()
    val first_name = text("first_name").nullable()
    val last_name = text("last_name").nullable()
    val secondary_mobile_number = text("secondary_mobile_number").nullable()
    val fax_number = text("fax_number").nullable()
    val street_address = text("street_address").nullable()
    val city = text("city").nullable()
    val identification_type = text("identification_type").nullable()
    val identification_no = text("identification_no").nullable()
    val occupation = text("occupation").nullable()
    val user_description = text("user_description").nullable()
    val marital_status = text("marital_status").nullable()
    val post_code = text("post_code").nullable()
    val gender = text("gender").nullable()
}

class UsersProfileEntity(id: EntityID<String>) : BaseIntEntity(id, UserProfileTable) {
    companion object : BaseIntEntityClass<UsersProfileEntity>(UserProfileTable)

    var user_id by UserProfileTable.user_id
    var user_profile_image by UserProfileTable.user_profile_image
    var first_name by UserProfileTable.first_name
    var last_name by UserProfileTable.last_name
    var secondary_mobile_number by UserProfileTable.secondary_mobile_number
    var fax_number by UserProfileTable.fax_number
    var street_address by UserProfileTable.street_address
    var city by UserProfileTable.city
    var identification_type by UserProfileTable.identification_type
    var identification_no by UserProfileTable.identification_no
    var occupation by UserProfileTable.occupation
    var user_description by UserProfileTable.user_description
    var marital_status by UserProfileTable.marital_status
    var post_code by UserProfileTable.post_code
    var gender by UserProfileTable.gender
    fun response() = UserProfile(
        user_id.value,
        user_profile_image,
        first_name,
        last_name,
        secondary_mobile_number,
        fax_number,
        street_address,
        city,
        identification_type,
        identification_no,
        occupation,
        user_description,
        marital_status,
        post_code,
        gender,
    )
}

data class UserProfile(
    var userId: String,
    val userProfileImage: String?,
    val firstName: String?,
    val lastName: String?,
    val secondaryMobileNumber: String?,
    val faxNumber: String?,
    val streetAddress: String?,
    val city: String?,
    val identificationType: String?,
    val identificationNo: String?,
    val occupation: String?,
    val userDescription: String?,
    val maritalStatus: String?,
    val postCode: String?,
    val gender: String?
)

