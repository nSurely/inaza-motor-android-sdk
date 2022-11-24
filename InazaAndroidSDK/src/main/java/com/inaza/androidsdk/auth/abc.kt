package com.inaza.androidsdk.auth
/*
Abstract base classes for any authentication methods.
Note that API keys should implement just the Base class.
While JWT should implment the Base and JWTAuth classes (TBD).
*/


/**
 * Auth base
 *
 * @constructor Create empty Auth base
 */
abstract class AuthBase {
    /**
     * Returns True if the auth token requires a refresh.
     *
     * @return
     */
    abstract suspend fun requiresRefresh(): Boolean

    /**
     * Refreshes the auth token
     *
     */
    abstract suspend fun refresh()

    /**
     * Returns the current headers.
     *
     * @return
     */
    abstract suspend fun getHeaders(): MutableMap<String, String>

    /**
     * Returns True if the auth token is valid. True always for API Key.
     *
     * @return
     */
    abstract suspend fun isLoggedIn(): Boolean

    /**
     * Returns True if the auth token is valid. True always for API Key.
     *
     * @param email
     * @param password
     * @return
     */
    abstract suspend fun login(email: String, password: String): String

    /**
     * Signs out the user/driver.
     *
     * @return
     */
    abstract suspend fun logout(): Boolean

    /**
     * Returns the current token
     *
     * @return
     */
    abstract suspend fun getToken(): String

    /**
     * Signup
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param fields
     * @param login
     * @return
     */
    abstract suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        fields: MutableMap<String, String>,
        login: Boolean = true
    ): Map<String, String>?
}

/**
 * Signs out the user/driver.
 *
 * @constructor Create empty Jwt auth base
 */
abstract class JwtAuthBase : AuthBase()

/**
 * Abstract base class for any JWT authentication method for drivers.
 *
 * @constructor Create empty Jwt driver auth base
 */
abstract class JwtDriverAuthBase : JwtAuthBase()

/**
 * Abstract base class for any JWT authentication method for users.
 *
 * @constructor Create empty Jwt user auth base
 */
abstract class JwtUserAuthBase : JwtAuthBase()