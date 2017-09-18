import com.brentwatson.droidcon2017.server.DataStore
import com.brentwatson.droidcon2017.server.Person
import com.google.gson.Gson
import org.jetbrains.ktor.host.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.content.*
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.*
import org.jetbrains.ktor.request.receiveParameters
import org.jetbrains.ktor.request.receiveText
import org.jetbrains.ktor.response.*
import org.jetbrains.ktor.routing.*
import org.jetbrains.ktor.util.raw
import java.io.File

fun main(args: Array<String>) {
    val gson = Gson()
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            post("/info") {
                val json = call.receiveText()
                val person = gson.fromJson(json, Person::class.java)
                DataStore.cache.add(person)
                call.respondText("OK\n")
            }
            post("/form") {
                val parameters = call.receiveParameters()
                val person = Person(
                        parameters["name"]!!, parameters["email"], parameters["phone_number"]
                )
                DataStore.cache.add(person)
                call.respondRedirect("/list")
            }
            get("/all") {
                call.respond(gson.toJson(DataStore.cache))
            }
            get("/list") {
                call.respondText(createHTML(prettyPrint = true)
                        .body {
                            h1 { +"Draw Submissions" }
                            div { id = "submissions" }
                            script(type = "text/javascript", src = "js/kotlin.js")
                            script(type = "text/javascript", src = "js/kotlinx-html-js.js")
                            script(type = "text/javascript", src = "js/frontend_main.js")
                        }, ContentType.Text.Html)
            }
            static {
                defaultResource("form.html", "html")
            }
            static("js") {
                staticRootFolder = File("./frontend")
                files("web")
            }
        }
    }
    server.start(wait = true)
}
