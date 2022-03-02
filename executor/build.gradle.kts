val ktor_version: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":command"))
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.contentNegotiation)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    testImplementation(project(":command:fakes"))
    testImplementation(Deps.Kotest.junit)
    testImplementation(Deps.Kotest.assertions)
}