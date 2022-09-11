package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Recipe(
    var recipeId: Int,
    var status: Int,
    var medicines: List<Medicine>
) {
    override fun toString(): String = Gson().toJson(this)
}
