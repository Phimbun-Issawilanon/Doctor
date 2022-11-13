package org.empowrco.doctor.tester.backend

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val testerBackendModule = module {
    singleOf(::RealTesterRepository) { bind<TesterRepository>() }
}
