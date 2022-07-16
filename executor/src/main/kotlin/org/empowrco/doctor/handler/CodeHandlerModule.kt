package org.empowrco.doctor.handler

import org.koin.dsl.module

internal val codeHandlerModule = module {
    single<CodeHandler> { RealCodeHandler(getAll()) }
}