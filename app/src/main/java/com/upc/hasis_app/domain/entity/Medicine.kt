package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Medicine(
    var medicineId: Int,
    var name: String,
    var weight: Int,
    var quantity: Int,
    var eachHour: Int,
    var prescribedDays: Int,
    var startDate: String,
    var endDate: String,
) {
    override fun toString(): String = Gson().toJson(this)
}
