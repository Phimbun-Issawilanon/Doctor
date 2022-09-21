package org.empowrco.doctor.executor.handler

import org.koin.dsl.module

internal val codeHandlerModule = module {
    single<CodeHandler> { RealCodeHandler(getAll()) }
}
