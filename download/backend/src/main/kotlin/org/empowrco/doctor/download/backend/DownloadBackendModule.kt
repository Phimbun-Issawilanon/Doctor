package org.empowrco.doctor.download.backend

import org.empowrco.doctor.download.backend.generator.Generator
import org.empowrco.doctor.download.backend.generator.SwiftGenerator
import org.empowrco.doctor.download.backend.handler.DownloadRepository
import org.empowrco.doctor.download.backend.handler.RealDownloadRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val downloadBackendModule = module {
    single<DownloadRepository> { RealDownloadRepository(getAll()) }
    single { SwiftGenerator() } bind Generator::class
}
