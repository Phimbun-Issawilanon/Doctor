package org.empowrco.doctor.assignment

import org.empowrco.doctor.assignment.backend.assignmentBackendModule
import org.empowrco.doctor.assignment.presenters.assignmentPresentersModule

val assignmentModule = listOf(assignmentPresentersModule, assignmentBackendModule)