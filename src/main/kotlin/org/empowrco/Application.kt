package org.empowrco

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.empowrco.plugins.configureHTTP
import org.empowrco.plugins.configureKoin
import org.empowrco.plugins.configureRouting
import org.empowrco.plugins.configureSerialization
import org.empowrco.plugins.configureStatusPages

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureHTTP()
        configureSerialization()
        configureStatusPages()
        configureKoin()
    }.start(wait = true)
}
