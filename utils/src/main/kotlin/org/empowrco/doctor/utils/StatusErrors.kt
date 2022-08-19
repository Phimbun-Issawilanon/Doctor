package org.empowrco.doctor.utils

object UnknownException : RuntimeException("Something bad happened.")
object UnauthorizedException : RuntimeException("User unauthenticated")
class UnsupportedLanguage(language: String) : RuntimeException("The server does not support $language")