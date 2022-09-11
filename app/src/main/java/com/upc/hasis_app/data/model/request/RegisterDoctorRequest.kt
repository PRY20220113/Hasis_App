package com.upc.hasis_app.data.model.request

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.upc.hasis_app.util.Constantes
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class RegisterDoctorRequest constructor(
    @SerializedName("dni")          var dni: String = "",
    @SerializedName("firstName")    var firstName: String = "",
    @SerializedName("lastName")     var lastName: String = "",
    @SerializedName("email")        var email: String = "",
    @SerializedName("password")     var password: String = "",
    @SerializedName("birthDate")    var birthDate: String = "",
    @SerializedName("phone")        var phone: String = "",
    @SerializedName("sex")          var sex: String = "",
    @SerializedName("license")      var license: String = "",

    ) {
    override fun toString() = Gson().toJson(this)
}

