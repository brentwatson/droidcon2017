package com.brentwatson.droidcon2017.shared
import com.google.gson.annotations.SerializedName

data class Person(
        val name: String,
        val email: String?,
        @SerializedName("phone_number") val phoneNumber: String?
)
