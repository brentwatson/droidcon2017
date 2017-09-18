package com.brentwatson.droidcon2017

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        submit.setOnClickListener {
            val person = Person(name.text(), email.text(), phone_number.text())
            val call = Network().postPerson(person)
            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) = clear()
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) =
                    Toast.makeText(this@MainActivity, "Error: ${t?.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }
    private fun clear() {
        name.clear()
        email.clear()
        phone_number.clear()
    }
}

fun EditText.text() = this.text.toString()
fun EditText.clear() = this.setText("")
