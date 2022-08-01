
plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = ConfigData.baseGroup
version = ConfigData.version
application {
    mainClass.set("org.empowrco.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xuse-experimental=io.ktor.locations.KtorExperimentalLocationsAPI",
            "-opt-in=kotlin.RequiresOptIn"
        ) + kotlinOptions.freeCompilerArgs
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

dependencies {

    implementation(project(Deps.Project.playground))
    implementation(project(Deps.Project.api))
    implementation(project(Deps.Project.executor))
    implementation(project(Deps.Project.Command.main))
    implementation(project(Deps.Project.models))
    implementation(Deps.Koin.main)
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.auth)
    implementation(Deps.Ktor.headers)
    implementation(Deps.Ktor.redirect)
    implementation(Deps.Ktor.statusPages)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.gson)
    implementation(Deps.Ktor.netty)
    implementation(Deps.Logback.main)
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}