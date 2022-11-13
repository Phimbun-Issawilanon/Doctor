package org.empowrco.doctor.tester.presenters

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val testerPresenterModule = module {
    singleOf(::RealTesterPresenter) { bind<TesterPresenter>() }
}
