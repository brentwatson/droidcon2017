import com.brentwatson.droidcon2017.server.DataStore
import com.brentwatson.droidcon2017.shared.Person
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
import java.io.File

fun main(args: Array<String>) {
    val gson = Gson()
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            static {
                defaultResource("form.html", "html")
            } // "/" path
            post("/form") {
                val parameters = call.receiveParameters()
                if (parameters["name"].isNullOrBlank()) {
                    call.respondRedirect("/")
                    return@post
                }
                val person = Person(
                        parameters["name"]!!,
                        parameters["email"],
                        parameters["phone_number"]
                )
                DataStore.cache.add(person)
                call.respondRedirect("/list")
            }
            post("/info") {
                val json = call.receiveText()
                gson.fromJson<Person>(json)?.let { person ->
                    DataStore.cache.add(person)
                }
                call.respondText("OK\n")
            }
            get("/all") {
                call.respond(gson.toJson(DataStore.cache))
            }
            get("/list") {
                call.respondText(createHTML(prettyPrint = true)
                        .body {
                            style { unsafe { raw("span {margin-bottom: 3px; display: inline-block;}") } }
                            h1 { +"Draw Submissions" }
                            div { id = "submissions" }
                            script(type = "text/javascript", src = "js/kotlin.js")
                            script(type = "text/javascript", src = "js/kotlinx-html-js.js")
                            script(type = "text/javascript", src = "js/frontend_main.js")
                        }, ContentType.Text.Html)
            }
            get("/dump") {
                File("./dump/dump.txt")
                        .writeText(
                                DataStore.cache.joinToString("\n") {
                                    with(it) { "$name $email $phoneNumber" }
                                }
                        )
                call.respondText("OK")
            }
            static("js") {
                staticRootFolder = File("./frontend")
                files("web")
            }
        }
    }
    server.start(wait = true)
}

private inline fun <reified T> Gson.fromJson(json: String): T? =
        this.fromJson(json, T::class.java)
