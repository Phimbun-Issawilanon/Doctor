package org.empowrco.doctor.models

sealed interface ExecutorResponse
open class Error(override val message: String) : ExecutorResponse, RuntimeException(message)
class NoValidExecutor(language: String) : Error(language)
data class Success(val output: String) : ExecutorResponse