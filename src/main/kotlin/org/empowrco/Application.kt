package org.empowrco

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.empowrco.plugins.configureHTTP
import org.empowrco.plugins.configureRouting
import org.empowrco.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}
