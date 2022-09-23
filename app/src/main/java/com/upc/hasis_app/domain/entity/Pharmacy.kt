package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Pharmacy(
    var pharmacyId: String,
    var name: String,
    var location: String,
    var distance: String
) {
    override fun toString(): String = Gson().toJson(this)
}


