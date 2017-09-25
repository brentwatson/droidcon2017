package com.brentwatson.droidcon2017

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.enqueue(
        success: (call: Call<T>?, response: Response<T>?) -> Unit,
        failure: (call: Call<T>?, t: Throwable?) -> Unit
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) = success(call, response)
        override fun onFailure(call: Call<T>?, t: Throwable?) = failure(call, t)
    })
}
