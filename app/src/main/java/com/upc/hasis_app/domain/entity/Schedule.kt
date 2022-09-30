package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime

data class Schedule(
    var medicineId: Int,
    var name: String,
    var weight: Int,
    var quantity: Int,
    var localDate: String
) {
    override fun toString(): String = Gson().toJson(this)
}

