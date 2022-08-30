package com.upc.hasis_app.domain.entity

import com.google.gson.Gson

data class Patient(
    var id: Int,
    var age: String,
    var allergy: String,
    var bloodt: String,
    var chronicd: String,
    var genre: String,
    var name: String,
    var doctor_id: String
) {
    override fun toString() = Gson().toJson(this)
}
