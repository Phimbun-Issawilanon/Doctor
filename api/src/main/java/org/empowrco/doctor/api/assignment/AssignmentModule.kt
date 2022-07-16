package org.empowrco.doctor.api.assignment

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val assignmentModule = module {
    singleOf(::RealAssignmentPresenter) { bind<AssignmentPresenter>() }
    singleOf(::RealAssignmentRepository) { bind<AssignmentRepository>() }
}