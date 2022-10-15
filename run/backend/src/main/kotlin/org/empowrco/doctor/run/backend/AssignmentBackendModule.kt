package org.empowrco.doctor.run.backend

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runBackendModule = module {
    singleOf(::RealRunRepository) { bind<RunRepository>() }
}
