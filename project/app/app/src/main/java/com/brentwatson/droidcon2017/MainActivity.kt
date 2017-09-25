package com.brentwatson.droidcon2017

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hookupSubmitButton()
    }

    private fun hookupSubmitButton() {
        submit.setOnClickListener {
            // Synthetic view properties...
            val person = Person(name.text(), email.text(), phone_number.text())
            // Retrofit call
            val call = Network().postPerson(person)
            // Retrofit `Call` extension
            call.enqueue(
                    { _, _ ->
                        // Anko Dialog builder
                        alert("Submitted") {
                            okButton {
                                clear()
                            }
                        }.show()
                    },
                    { _, err -> toast("Error: ${err?.message}") }
            )
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
