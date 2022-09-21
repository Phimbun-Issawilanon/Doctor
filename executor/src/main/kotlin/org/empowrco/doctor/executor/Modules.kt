package org.empowrco.doctor

import org.empowrco.doctor.executor.executorModule
import org.empowrco.doctor.executor.handler.codeHandlerModule

val executorModules = listOf(codeHandlerModule, executorModule)
