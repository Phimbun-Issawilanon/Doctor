object Deps {

    object Project {
        val models by lazy { ":models" }
        val playground by lazy { ":playground" }
        val routingUtils by lazy { ":routing-utils" }
        val executor by lazy { ":executor" }
        val api by lazy { ":api" }

        object Command {
            val main by lazy { ":command" }
            val fakes by lazy { ":command:fakes" }
        }

    }

    object Koin {
        val main by lazy { "io.insert-koin:koin-ktor:${Versions.koin}" }
        val logger by lazy { "io.insert-koin:koin-logger-slf4j:${Versions.koin}" }
    }

    object Kotest {
        val junit by lazy { "io.kotest:kotest-runner-junit5:${Versions.kotest}" }
        val assertions by lazy { "io.kotest:kotest-assertions-core:${Versions.kotest}" }
    }

    object Kotlin {
        val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2" }
    }

    object Ktor {
        val core by lazy { "io.ktor:ktor-server-core-jvm:${Versions.ktor}" }
        val auth by lazy { "io.ktor:ktor-server-auth-jvm:${Versions.ktor}" }
        val headers by lazy { "io.ktor:ktor-server-caching-headers-jvm:${Versions.ktor}" }
        val redirect by lazy { "io.ktor:ktor-server-http-redirect-jvm:${Versions.ktor}" }
        val contentNegotiation by lazy { "io.ktor:ktor-server-content-negotiation-jvm:${Versions.ktor}" }
        val json by lazy { "io.ktor:ktor-serialization-kotlinx-json-jvm:${Versions.ktor}" }
        val gson by lazy { "io.ktor:ktor-client-gson:${Versions.ktor}" }
        val netty by lazy { "io.ktor:ktor-server-netty-jvm:${Versions.ktor}" }
        val statusPages by lazy { "io.ktor:ktor-server-status-pages:${Versions.ktor}" }
    }

    object Logback {
        val main by lazy { "ch.qos.logback:logback-classic:${Versions.logback}" }
    }

    object Archiver {
        val main by lazy { "org.rauschig:jarchivelib:1.2.0" }
    }

}