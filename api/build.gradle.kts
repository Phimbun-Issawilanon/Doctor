plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
}

dependencies {
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Ktor.statusPages)
    implementation(Deps.Koin.main)
    implementation(project(Deps.Project.models))
    implementation(project(Deps.Project.executor))
}
