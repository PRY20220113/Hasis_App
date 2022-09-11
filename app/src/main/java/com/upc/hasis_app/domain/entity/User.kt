package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class User(
    var userId: Int,
    var dni: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: String,
    var age: Int,
    var phone: String,
    var sex: String,
    var registerDate: String,
    var rol: String,
) {
    override fun toString(): String = Gson().toJson(this)
}
