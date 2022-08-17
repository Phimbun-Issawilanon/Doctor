object Deps {

    object Project {
        val playground by lazy { ":playground" }
        val executor by lazy { ":executor" }
        val assignment by lazy { ":assignment" }

        object Command {
            val main by lazy { ":command" }
            val fakes by lazy { ":command:fakes" }
        }
    }

    object Assignment {
        val api by lazy { ":assignment:api" }
        val presenters by lazy { ":assignment:presenters" }
        val backend by lazy { ":assignment:backend" }
        val main by lazy { ":assignment" }
    }

    object Utils {
        val routing by lazy { ":utils:routing" }
        val main by lazy { ":utils" }
    }

    object Exposed {
        val core by lazy { "org.jetbrains.exposed:exposed-core:${Versions.exposed}" }
        val jdbc by lazy { "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}" }
        val dateTime by lazy { "org.jetbrains.exposed:exposed-kotlin-datetime:${Versions.exposed}" }
        val javaDateTime by lazy { "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}" }
    }

    object Hikari {
        val main by lazy { "com.zaxxer:HikariCP:5.0.1" }
    }

    object Postgresql {
        val main by lazy { "org.postgresql:postgresql:42.3.1" }
    }

    object Models {
        val main by lazy { ":models" }
    }

    object Db {
        val main by lazy { ":db" }
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
        val dateTime by lazy { "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0" }
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

    object Sources {
        val main by lazy { ":sources" }
    }

    object Logback {
        val main by lazy { "ch.qos.logback:logback-classic:${Versions.logback}" }
    }

    object Archiver {
        val main by lazy { "org.rauschig:jarchivelib:1.2.0" }
    }

}