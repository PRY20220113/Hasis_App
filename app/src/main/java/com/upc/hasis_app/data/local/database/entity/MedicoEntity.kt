package com.upc.hasis_app.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "medico")
class MedicoEntity {

    @Expose(serialize = false)
    //@PrimaryKey(autoGenerate = true)
    private val uid: Int? = null

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private val name: String? = null

    @SerializedName("surname")
    @Expose
    @ColumnInfo(name = "surname")
    private val surname: String? = null

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private val email: String? = null

}