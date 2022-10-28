package org.empowrco.doctor.run.presenters

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentersModule = module {
    singleOf(::RealRunPresenter) { bind<RunPresenter>() }
}
