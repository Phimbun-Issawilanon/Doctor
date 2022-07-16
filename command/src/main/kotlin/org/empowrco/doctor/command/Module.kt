package org.empowrco.doctor.command

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commandModule = module {
    singleOf(::RealCommander) { bind<Commander>() }
}