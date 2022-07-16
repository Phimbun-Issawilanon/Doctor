package org.empowrco.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.empowrco.doctor.models.NoValidExecutor

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<NoValidExecutor> { call, cause ->
            respond(call, cause, HttpStatusCode.NotFound)
        }
        exception<CannotTransformContentToTypeException> { call, cause ->
            respond(call, cause, HttpStatusCode.BadRequest)
        }
        exception<Throwable> { call, cause ->
            respond(call, cause, HttpStatusCode.InternalServerError)
        }
    }
}

private suspend fun respond(
    call: ApplicationCall,
    cause: Throwable,
    status: HttpStatusCode,
) {
    call.respond(status, ErrorStatus(cause.message))
}

@kotlinx.serialization.Serializable
private data class ErrorStatus(val error: String?)