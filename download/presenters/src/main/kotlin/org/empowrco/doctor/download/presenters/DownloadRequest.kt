package org.empowrco.doctor.download.presenters

@kotlinx.serialization.Serializable
data class DownloadRequest(
    val language: String,
    val code: String,
)
