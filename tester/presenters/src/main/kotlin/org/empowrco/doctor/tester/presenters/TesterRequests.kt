package org.empowrco.doctor.tester.presenters

data class ExecuteTestRequest(
    val language: String,
    val tests: String,
    val code: String,
)
