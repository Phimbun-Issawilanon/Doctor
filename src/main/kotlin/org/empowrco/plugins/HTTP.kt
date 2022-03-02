package org.empowrco.plugins

import io.ktor.server.plugins.*
import io.ktor.http.content.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureHTTP() {
    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> null
            }
        }
    }
/*    install(HttpsRedirect) {
        // The port to redirect to. By default 443, the default HTTPS port.
        sslPort = 443
        // 301 Moved Permanently, or 302 Found redirect.
        permanentRedirect = true
    }*/

}
