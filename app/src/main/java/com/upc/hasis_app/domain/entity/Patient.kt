package com.upc.hasis_app.domain.entity

import com.google.gson.Gson

data class Patient(
    var patientId: Int,
    var bloodT: String,
    var chronicD: String,
    var allergy: String,
    var user: User
) {
    override fun toString() = Gson().toJson(this)
}
