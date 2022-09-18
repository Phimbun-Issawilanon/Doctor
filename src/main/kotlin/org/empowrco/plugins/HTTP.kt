package org.empowrco.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.httpsredirect.HttpsRedirect

fun Application.configureHTTP() {
    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                else -> null
            }
        }
    }
    if (!System.getenv("DEBUG").toBoolean()) {
        install(HttpsRedirect) {
            // The port to redirect to. By default, 443, the default HTTPS port.
            sslPort = 443
            // 301 Moved Permanently, or 302 Found redirect.
            permanentRedirect = true
        }
    }
}
