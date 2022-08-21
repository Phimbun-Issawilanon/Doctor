package org.empowrco.doctor.download

import org.empowrco.doctor.download.backend.downloadBackendModule
import org.empowrco.doctor.download.presenters.downloadPresenterModule

val downloadModule = listOf(downloadBackendModule, downloadPresenterModule)
