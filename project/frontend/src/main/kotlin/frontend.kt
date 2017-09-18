import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Json

fun main(args: Array<String>) {
    getData { people ->
        for (person in people) {
            document.body?.append?.div {
                unsafe { raw( person.toHtml() ) }
            }
        }
    }
}

fun getData(displayData: (people: List<Person>) -> Unit) {
    val req = XMLHttpRequest().apply {
        onloadend = fun(_: Event) {
            val items = JSON.parse<Array<Json>>(responseText)
            val people = items.map {
                Person(it["name"] as String,
                        it["email"] as? String,
                        it["phone_number"] as? String
                )
            }
            println(people)
            displayData(people)
        }
    }
    with(req) {
        open("GET", "/all", true)
        send()
    }
}

data class Person(
        val name: String,
        val email: String?,
        val phoneNumber: String?
)

fun Person.toHtml() = createHTML().div {
    span {
        b { +name }
        email?.let { i { +" $it" } }
        phoneNumber?.let { span { +" $it" } }
    }
}
