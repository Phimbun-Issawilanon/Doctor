package org.empowrco.doctor.sources

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sourcesModule = module {
    singleOf(::RealAssignmentSource) { bind<AssignmentSource>() }
}