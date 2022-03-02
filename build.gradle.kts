
plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
}

group = ConfigData.baseGroup
version = ConfigData.version
application {
    mainClass.set("org.empowrco.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {

    implementation(project(":playground"))
    implementation(project(":executor"))

    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.auth)
    implementation(Deps.Ktor.headers)
    implementation(Deps.Ktor.redirect)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.gson)
    implementation(Deps.Ktor.netty)
    implementation(Deps.Logback.main)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}