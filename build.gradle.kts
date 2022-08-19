import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = ConfigData.baseGroup
version = ConfigData.version
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
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
    implementation(project(Deps.Assignment.main))
    implementation(project(Deps.Assignment.api))
    implementation(project(Deps.Project.executor))
    implementation(project(Deps.Sources.main))
    implementation(project(Deps.Project.Command.main))
    implementation(project(Deps.Db.main))
    implementation(project(Deps.Utils.routing))
    implementation(Deps.Koin.main)
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.auth)
    implementation(Deps.Ktor.headers)
    implementation(Deps.Ktor.redirect)
    implementation(Deps.Exposed.dateTime)
    implementation(Deps.Ktor.statusPages)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.gson)
    implementation(Deps.Ktor.netty)
    implementation(Deps.Logback.main)
    implementation(project(Deps.Utils.main))
    implementation(kotlin("stdlib-jdk8"))
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}