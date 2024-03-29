package com.piashcse.models.category

import com.papsign.ktor.openapigen.annotations.parameters.QueryParam
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class AddProductCategory(@QueryParam("categoryName") val categoryName: String) {
    fun validation() {
        validate(this) {
            validate(AddProductCategory::categoryName).isNotNull().isNotEmpty()
        }
    }
}