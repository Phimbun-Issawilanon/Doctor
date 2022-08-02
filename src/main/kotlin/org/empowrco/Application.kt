package org.empowrco

import io.ktor.server.application.Application
import org.empowrco.plugins.configureHTTP
import org.empowrco.plugins.configureKoin
import org.empowrco.plugins.configureRouting
import org.empowrco.plugins.configureSerialization
import org.empowrco.plugins.configureStatusPages

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
fun Application.module() {
    configureRouting()
    configureHTTP()
    configureSerialization()
    configureStatusPages()
    configureKoin()
}