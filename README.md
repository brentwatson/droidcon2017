# Droidcon NYC 2017 Demo App

Full-Stack Kotlin App

Presentation here: https://bit.ly/droidcon-fullstack-kotlin

This demo app has the following Kotlin modules:
- `server`: Server/REST endpoints using [ktor](http://ktor.io/), can be built with gradle or [kobalt](beust.com/kobalt)
- `frontend`: Uses [kotlin-js](https://kotlinlang.org/docs/tutorials/javascript/kotlin-to-javascript/kotlin-to-javascript.html) and [kotlinx-html-js](https://github.com/Kotlin/kotlinx.html)
- `native`: Command-line tool build with Kotlin Native
- `app`: Small Kotlin Android App with [Retrofit](https://github.com/square/retrofit), [Gson](https://github.com/google/gson), [Android Extensions](https://kotlinlang.org/docs/tutorials/android-plugin.html), and [Anko](https://github.com/Kotlin/anko)
- `shared`: Shared model objects
