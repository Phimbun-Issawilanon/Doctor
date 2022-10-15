package org.empowrco.doctor.run

import org.empowrco.doctor.run.backend.runBackendModule
import org.empowrco.doctor.run.presenters.runPresentersModule

val runModule = listOf(runPresentersModule, runBackendModule)
