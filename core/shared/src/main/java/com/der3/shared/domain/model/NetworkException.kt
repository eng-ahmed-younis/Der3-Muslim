package com.der3.shared.domain.model

import java.io.IOException



/**
 * Many low-level libraries (like OkHttp and Ktor) throw IOException
 * when a connection is lost or a socket times out
 * Inheriting from IOException ensures compatibility with standard Android network
 * libraries and built-in "retry" mechanisms. It also semantically identifies the error
 * as a communication failure, consistent with the standard Java/Kotlin I/O hierarchy.
 * */
sealed class NetworkException(
    val errorMessage: String? = null,
    val errorCode: Int? = null
) : IOException(errorMessage) {
    class BadRequestException(message: String?, code: Int?) : NetworkException(message, code)
    class UnauthorizedException(message: String?, code: Int?) : NetworkException(message, code)
    class ForbiddenException(message: String?, code: Int?) : NetworkException(message, code)
    class NotFoundException(message: String?, code: Int?) : NetworkException(message, code)
    class InternalServerException(message: String?, code: Int?) : NetworkException(message, code)
    class ServiceUnavailableException(message: String?, code: Int?) : NetworkException(message, code)
    class UnknownNetworkException(message: String?, code: Int?) : NetworkException(message, code)
    class NoInternetException : NetworkException("No internet connection")
    class TimeoutException : NetworkException("Request timeout")
}
