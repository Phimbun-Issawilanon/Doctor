plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(Deps.Command.main))
    implementation(project(Deps.Models.main))
    testImplementation(project(Deps.Command.fakes))
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Koin.main)
    implementation(project(Deps.Utils.files))
}
