package com.example.controller

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.entities.*
import com.example.models.*
import com.example.utils.AppConstants
import com.example.utils.currentTimeInUTC
import com.example.utils.toMap
import com.google.gson.Gson
import kotlinx.datetime.LocalDateTime
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.random.Random
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class UserController {
    fun registration(registrationBody: RegistrationBody) = transaction {
        val userEntity = UsersEntity.find { UsersTable.email eq registrationBody.email }.toList().singleOrNull()
        return@transaction if (userEntity == null) {
            val inserted = UsersEntity.new(UUID.randomUUID().toString()) {
                user_name = registrationBody.userName
                email = registrationBody.email
                password = BCrypt.withDefaults().hashToString(12, registrationBody.password.toCharArray())
                updated_at = java.time.LocalDateTime.now()
            }
            UsersProfileEntity.new(UUID.randomUUID().toString()) {
                user_id = inserted.id
                updated_at = java.time.LocalDateTime.now()
            }
            UserHasTypeEntity.new(UUID.randomUUID().toString()) {
                user_id = inserted.id
                user_type_id = registrationBody.userType
                created_at = currentTimeInUTC().toString()
                updated_at = currentTimeInUTC().toString()
            }
            RegistrationResponse(registrationBody.userName, registrationBody.email)
        } else null
    }

    fun login(loginBody: LoginBody) = transaction {
        val query = UsersTable.leftJoin(UserHasTypeTable).select { UsersTable.email eq loginBody.email }
        val result = UsersEntity.wrapRows(query).first()
        if (BCrypt.verifyer().verify(loginBody.password.toCharArray(), result.password).verified && result.userType.user_type_id == loginBody.userType) {
            return@transaction result.userResponse()
        } else null
    }

    fun jwtVerification(jwtTokenBody: JwtTokenBody) = transaction {
        val usersEntity = UsersEntity.find { UsersTable.email eq jwtTokenBody.email }.toList().singleOrNull()
        println("user : ${usersEntity?.userType}")
        usersEntity?.let {
            return@transaction if (usersEntity != null) {
               // JwtTokenBody(usersEntity.id.value, usersEntity.email)
                JwtTokenBody(usersEntity.id.value, usersEntity.email, usersEntity.userType.user_type_id)
            } else null
        }
    }

    fun changePassword(userId: String, changePassword: ChangePassword) = transaction {
        val userEntity = UsersEntity.find { UsersTable.id eq userId }.toList().singleOrNull()
        return@transaction userEntity?.let {
            if (BCrypt.verifyer().verify(changePassword.oldPassword.toCharArray(), it.password).verified) {
                it.password = BCrypt.withDefaults().hashToString(12, changePassword.newPassword.toCharArray())
                it.updated_at = java.time.LocalDateTime.now()
                it
            } else {
                return@transaction changePassword
            }
        }
    }

    fun forgetPassword(forgetPasswordBody: ForgetPasswordBody) = transaction {
        val userEntity = UsersEntity.find { UsersTable.email eq forgetPasswordBody.email }.toList().singleOrNull()
        return@transaction userEntity?.let {
            val verificationCode = Random.nextInt(1000, 9999).toString()
            it.verification_code = verificationCode
            VerificationCode(verificationCode)
        }
    }

    fun confirmPassword(confirmPasswordBody: ConfirmPasswordBody) = transaction {
        val userEntity = UsersEntity.find { UsersTable.email eq confirmPasswordBody.email }.toList().singleOrNull()
        return@transaction userEntity?.let {
            if (confirmPasswordBody.verificationCode == it.verification_code) {
                it.password = BCrypt.withDefaults().hashToString(12, confirmPasswordBody.password.toCharArray())
                it.verification_code = null
                AppConstants.DataBaseTransaction.FOUND
            } else {
                AppConstants.DataBaseTransaction.NOT_FOUND
            }
        }
    }


    fun updateProfile(userId: String, userProfile: UserProfile?) = transaction {
        val userProfileEntity = UsersProfileEntity.find { UsersProfileTable.user_id eq userId }.toList().singleOrNull()
        return@transaction userProfileEntity?.let {
            it.user_profile_image = userProfile?.userProfileImage ?: it.user_profile_image
            it.first_name = userProfile?.firstName ?: it.first_name
            it.last_name = userProfile?.lastName ?: it.last_name
            it.secondary_mobile_number = userProfile?.secondaryMobileNumber ?: it.secondary_mobile_number
            it.fax_number = userProfile?.faxNumber ?: it.fax_number
            it.street_address = userProfile?.streetAddress ?: it.street_address
            it.city = userProfile?.city ?: it.city
            it.identification_type = userProfile?.identificationType ?: it.identification_type
            it.identification_no = userProfile?.identificationNo ?: it.identification_no
            it.occupation = userProfile?.occupation ?: it.occupation
            it.user_description = userProfile?.userDescription ?: it.user_description
            it.marital_status = userProfile?.maritalStatus ?: it.marital_status
            it.post_code = userProfile?.postCode ?: it.post_code
            it.gender = userProfile?.gender ?: it.gender
            it.updated_at = java.time.LocalDateTime.now()
            it
        }
    }

}
