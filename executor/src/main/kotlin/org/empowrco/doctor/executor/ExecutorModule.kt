package org.empowrco.doctor.executor

import org.koin.dsl.bind
import org.koin.dsl.module

val executorModule = module {
    single { SwiftExecutor(get()) } bind Executor::class
    single { KotlinExecutor(get()) } bind Executor::class
    single { JavascriptExecutor(get()) } bind Executor::class
    single { TypescriptExecutor(get()) } bind Executor::class
}
