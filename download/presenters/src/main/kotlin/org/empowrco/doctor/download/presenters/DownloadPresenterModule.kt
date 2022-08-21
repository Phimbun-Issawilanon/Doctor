package org.empowrco.doctor.download.presenters

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val downloadPresenterModule = module {
    singleOf(::RealDownloadPresenter) { bind<DownloadPresenter>() }

}
