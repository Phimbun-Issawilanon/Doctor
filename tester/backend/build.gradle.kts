dependencies {
    implementation(project(Deps.Sources.main))
    implementation(project(Deps.Models.main))
    implementation(Deps.Koin.main)
    implementation(project(Deps.Executor.main))
    testImplementation(project(Deps.Executor.fakes))
    testImplementation(project(Deps.Sources.fakes))
    testImplementation(Deps.Kotlin.dateTime)
    testImplementation(project(Deps.Utils.main))
}

