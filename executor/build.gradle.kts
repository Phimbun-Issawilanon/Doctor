plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Deps.Project.Command.main))
    implementation(project(Deps.Models.main))
    testImplementation(project(Deps.Project.Command.fakes))
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Koin.main)
    testImplementation(Deps.Kotest.junit)
    testImplementation(Deps.Kotest.assertions)
}