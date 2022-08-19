package org.empowrco.doctor.utils.routing

import io.ktor.http.Parameters
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.authorization
import io.ktor.server.request.receiveOrNull
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import org.empowrco.doctor.utils.UnauthorizedException
import org.slf4j.LoggerFactory
import java.io.File

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.handleAuthenticatedPost(block: (body: JsonObject) -> T) {
    handleAuthentication()
    val requestBodyString = call.receiveOrNull<String>() ?: run {
        handleCallResponseException(call, Exception("No body in the request"))
        return
    }
    try {
        val requestBody = Json.parseToJsonElement(requestBodyString).jsonObject
        handleRequestBody(requestBody, block)
    } catch (ex: Exception) {
        handleCallResponseException(call, ex)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleAuthentication() {
    call.request.authorization()?.let {
        val possibleToken = it.removePrefix("Bearer").trim()
        if (possibleToken.isBlank()) {
            throw UnauthorizedException
        } else {
            possibleToken
        }
    }?.let {
        if (it != System.getenv("SECRET")) {
            throw UnauthorizedException
        }
    } ?: throw throw UnauthorizedException
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.handleAuthenticatedGet(block: (body: Parameters) -> T) {
    handleAuthentication()
    val params = call.request.queryParameters
    try {
        handleRequestParams(params, block)
    } catch (ex: Exception) {
        handleCallResponseException(call, ex)
    }
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.handleRequestParams(
    params: Parameters,
    block: (body: Parameters) -> T,
) {
    val response = block(params)
    call.respond(response)
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.handleRequestBody(
    requestBody: JsonObject,
    block: (body: JsonObject) -> T
) {
    val response = block(requestBody)
    if (response is File) {
        call.respondFile(response)
    } else {
        call.respond(response)
    }
}

suspend fun handleCallResponseException(
    call: ApplicationCall,
    error: Exception,
) {
    call.respond(
        JsonObject(
            mapOf(
                "error" to JsonPrimitive(error.localizedMessage)
            )
        )
    )
    val logger = LoggerFactory.getLogger("Routing")
    logger.error(error.toString())
}