val ktor_version: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "org.empowrco"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":routing-utils"))
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.json)
    implementation(Deps.Archiver.main)
    implementation(Deps.Ktor.contentNegotiation)
    testImplementation(Deps.Kotest.junit)
    testImplementation(Deps.Kotest.assertions)
}