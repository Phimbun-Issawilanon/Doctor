package org.empowrco.doctor.executor

import org.empowrco.doctor.models.ExecutorResponse


 abstract class Executor {
     abstract val handledLanguages: Set<String>
     abstract val canTest: Boolean
     abstract suspend fun execute(code: String): ExecutorResponse
     abstract suspend fun test(code: String, unitTests: String): ExecutorResponse
     fun canHandle(language: String): Boolean {
         return handledLanguages.contains(language)
     }
 }

