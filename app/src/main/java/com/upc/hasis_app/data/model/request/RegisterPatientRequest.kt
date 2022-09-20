package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RegisterPatientRequest (
    @SerializedName("dni")        var dni: String  = "",
    @SerializedName("firstName")  var firstName: String = "",
    @SerializedName("lastName")   var lastName: String = "",
    @SerializedName("email")      var email: String  = "",
    @SerializedName("password")   var password: String = "",
    @SerializedName("birthDate")  var birthDate: String = "",
    @SerializedName("phone")      var phone: String = "",
    @SerializedName("sex")        var sex: String = "",
    @SerializedName("bloodT")     var bloodT: String = "",
    @SerializedName("chronicD")   var chronicD: String = "",
    @SerializedName("allergy")    var allergy: String = "",
)
{
    override fun toString() = Gson().toJson(this)
}