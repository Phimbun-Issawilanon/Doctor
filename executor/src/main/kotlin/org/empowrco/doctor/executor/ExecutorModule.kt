package org.empowrco.doctor.executor

import org.koin.dsl.bind
import org.koin.dsl.module

val executorModule = module {
    single { SwiftExecutor(get(), get()) } bind Executor::class
    single { KotlinExecutor(get(), get()) } bind Executor::class
    single { JavascriptExecutor(get(), get()) } bind Executor::class
    single { TypescriptExecutor(get(), get()) } bind Executor::class
    single { PythonExecutor(get(), get()) } bind Executor::class
    single { RustExecutor(get(), get()) } bind Executor::class
}
