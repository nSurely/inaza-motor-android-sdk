package auth

class AuthError(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

class ApiKeyAuthError(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

class JwtAuthError(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

class DriverCreateError(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}