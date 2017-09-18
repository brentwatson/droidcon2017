package com.brentwatson.droidcon2017

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class Network {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val personService by lazy {
        retrofit.create(PersonService::class.java)
    }

    interface PersonService {
        @POST("/info")
        fun post(@Body person: Person): Call<ResponseBody>
    }

    fun postPerson(person: Person) = personService.post(person)

}
