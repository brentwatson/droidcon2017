import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.kotlin.*

val bs = buildScript {
    repos("http://dl.bintray.com/kotlin/ktor")
    repos("https://dl.bintray.com/kotlin/kotlinx")
}

val shared = project {
    directory = "../shared"
    name = "shared"
    group = "com.brentwatson.droidcon2017"
    artifactId = name
    version = "0.1"

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-runtime:1.1.4-2")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.4-2")
        compile("com.google.code.gson:gson:2.8.1")
    }

    assemble {
        jar {
        }
    }

    kotlinCompiler {
        args("-jvm-target", "1.8")
    }
}

val server = project(shared) {
    name = "server"
    group = "com.brentwatson.droidcon2017"
    artifactId = name
    version = "0.1"

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-runtime:1.1.4-2")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.4-2")
        compile("org.jetbrains.ktor:ktor-core:0.4.0")
        compile("org.jetbrains.ktor:ktor-netty:0.4.0")
        compile("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.4")
        compile("ch.qos.logback:logback-classic:1.2.1")
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")
    }

    assemble {
        jar {
        }
    }

    kotlinCompiler {
        args("-jvm-target", "1.8")
    }
}
