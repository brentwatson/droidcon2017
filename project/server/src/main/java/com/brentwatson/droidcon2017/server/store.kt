package com.brentwatson.droidcon2017.server

import com.brentwatson.droidcon2017.shared.Person

object DataStore {
    val cache = mutableSetOf<Person>()
}
