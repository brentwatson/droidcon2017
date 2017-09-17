import com.brentwatson.droidcon2017.server.DataStore
import com.brentwatson.droidcon2017.server.Person
import com.google.gson.Gson
import org.jetbrains.ktor.content.files
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.content.staticRootFolder
import org.jetbrains.ktor.host.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.*
import org.jetbrains.ktor.request.receiveText
import org.jetbrains.ktor.response.*
import org.jetbrains.ktor.routing.*
import java.io.File

fun main(args: Array<String>) {
    val gson = Gson()
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText(createHTML(prettyPrint = true)
                        .body {
                            script(type = "text/javascript", src = "js/kotlin.js")
                            script(type = "text/javascript", src = "js/frontend_main.js")
                    h1 { +"Draw Submissions" }
                }, ContentType.Text.Html)

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
            get("/admin") {
                call.respond(gson.toJson(DataStore.cache))
            }
            static("js") {
                staticRootFolder = File("./frontend")
                files("web")
            }
        }
    }
    server.start(wait = true)
}
