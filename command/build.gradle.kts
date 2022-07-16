dependencies {
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Koin.main)

    val kotestVersion = "5.1.0"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}