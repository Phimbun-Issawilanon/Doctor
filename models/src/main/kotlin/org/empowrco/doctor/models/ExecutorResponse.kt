package org.empowrco.doctor.models

sealed interface ExecutorResponse
open class Error(override val message: String) : ExecutorResponse, RuntimeException(message)
data class NoValidExecutor(private val language: String) : Error(language)
data class Untestable(private val language: String) :
    Error("$language does not support unit tests. Please upgrade the server to do so.")

data class Success(val output: String, val isStacktraceError: Boolean) : ExecutorResponse

sealed interface TestResponse : ExecutorResponse
