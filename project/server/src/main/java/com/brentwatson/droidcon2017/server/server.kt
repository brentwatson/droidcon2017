import com.brentwatson.droidcon2017.server.DataStore
import com.brentwatson.droidcon2017.server.Person
import com.google.gson.Gson
import org.jetbrains.ktor.host.*
import org.jetbrains.ktor.http.*
import org.jetbrains.ktor.netty.*
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.request.receiveText
import org.jetbrains.ktor.response.*
import org.jetbrains.ktor.routing.*

fun main(args: Array<String>) {
    val gson = Gson()
    val server = embeddedServer(Netty, port=8080) {
        routing {
            get("/") {
                call.respondText("Hello, world!\n", ContentType.Text.Html)
            }
            post("/info") {
                val json = call.receiveText()
                val person = gson.fromJson(json, Person::class.java)
                DataStore.cache.add(person)
                call.respondText("OK\n")
            }
            get("/all") {
                call.respond(gson.toJson(DataStore.cache))
            }
        }
    }
    server.start(wait = true)
}
