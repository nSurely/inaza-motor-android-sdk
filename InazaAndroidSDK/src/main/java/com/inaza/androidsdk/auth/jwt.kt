package com.inaza.androidsdk.auth

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlin.properties.Delegates

/**
 * Driver auth token
 *
 * @property accessToken
 * @property refreshToken
 * @property refreshExpiresIn
 * @property tokenType
 * @property expiresIn
 * @property accountType
 * @property orgId
 * @property accountId
 * @constructor Create empty Driver auth token
 */
@Serializable
data class DriverAuthToken(
    val accessToken: String,
    val refreshToken: String,
    val refreshExpiresIn: Int,
    val tokenType: String,
    val expiresIn: Int,
    val accountType: String,
    val orgId: String,
    val accountId: String
)

/**
 * Auth tokens
 *
 * @property accessToken
 * @property refreshToken
 * @property refreshExpiresIn
 * @property tokenType
 * @property expiresIn
 * @property accountType
 * @property orgId
 * @property accountId
 * @constructor Create empty Auth tokens
 */
@Serializable
data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val refreshExpiresIn: Int,
    val tokenType: String,
    val expiresIn: Int,
    val accountType: String,
    val orgId: String,
    val accountId: String
)

/**
 * Login
 *
 * @property email
 * @property password
 * @constructor Create empty Login
 */
@Serializable
data class Login(val email: String, val password: String)

/**
 * Refresh token
 *
 * @property refreshToken
 * @constructor Create empty Refresh token
 */
@Serializable
data class RefreshToken(val refreshToken: String)

/**
 * Jwt auth
 *
 * @property url
 * @property orgId
 * @property authType
 * @property email
 * @property password
 * @constructor Create empty Jwt auth
 */
open class JwtAuth(
    val url: String,
    val orgId: String,
    val authType: String,
    val email: String,
    val password: String
) : JwtAuthBase() {
    var accessToken: String = ""
    var tokenType: String = ""
    var expiresIn: Int = 0
    lateinit var refreshToken: String
    var refreshExpiresIn by Delegates.notNull<Int>()

    // user or driver
    lateinit var accountId: String
    lateinit var accountType: String

    //only used for users
    var orgs = arrayOf(String)
    var lastRefreshTime by Delegates.notNull<Long>()
    var headers = mutableMapOf<String, String>()
//      Always null?
//        if(authType == null){
//            println()
//        }
    // if not url

    // depends if user or driver
    var loginUrl: String
    var refreshUrl: String
    var logoutUrl: String

    init {
        if (authType == "user") {
            loginUrl = "${this.url}/org/auth/users/login"
            refreshUrl = "${this.url}/org/auth/users/session/refresh"
            logoutUrl = "${this.url}/org/auth/users/logout"
        } else if (authType == "driver") {
            if (orgId == null) {
                //raise exception
                println("F")
            }
            loginUrl = "${this.url}/org/${this.orgId}/auth/drivers/login"
            refreshUrl = "${this.url}/org/${this.orgId}/auth/drivers/session/refresh"
            logoutUrl = "${this.url}/org/${this.orgId}/auth/drivers/logout"
        } else {
            throw JwtAuthError("Auth type must be user or driver")
        }
    }

    /**
     * Check if access token has expired.
     *
     * @return Boolean: True if token has expired, False otherwise.
     */
    override suspend fun requiresRefresh(): Boolean {
        if (this.accessToken == "") {
            Log.e("Auth","no access token")

            return true
        }
        if (this.expiresIn <= 0) {
            Log.e("Auth","access token expired")
            return true
        }


        if (this.lastRefreshTime <= 0) {
            Log.e("Auth","last refresh empty")

            return true
        }

        if (((System.currentTimeMillis() / 1000) - this.lastRefreshTime) >= (this.expiresIn - 5)) {
            Log.e("Auth","Expired accessToken")

            return true
        }
        // Check if refresh token is expired?
        Log.e("Faslse","wfw")
        return false
    }

    /**
     * Login to the API
     * @param email
     * @param password
     * @return
     */
    override suspend fun login(email: String, password: String): String {
        val client = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
        val tokens: AuthTokens =
            client.post(this.loginUrl) {
                contentType(ContentType.Application.Json)
                body = (Login(email, password))
            }
        this.tokenType = tokens.tokenType
        this.accessToken = tokens.accessToken
        this.expiresIn = tokens.expiresIn
        this.refreshToken = tokens.refreshToken
        this.refreshExpiresIn = tokens.refreshExpiresIn

        this.lastRefreshTime = (System.currentTimeMillis() / 1000)
        return this.accessToken
    }

    /**
     * Refresh the access token.
     *
     */
    override suspend fun refresh() {
        // if not logged in check

        if (this.isLoggedIn()) {
            throw JwtAuthError("Not logged in")
        }
        val client = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
        val refreshToken = RefreshToken(this.refreshToken)
        val tokens: AuthTokens =
            client.post(this.refreshUrl) {
                contentType(ContentType.Application.Json)
                body = refreshToken
            }

        this.tokenType = tokens.tokenType
        this.accessToken = tokens.accessToken
        this.expiresIn = tokens.expiresIn
        this.refreshToken = tokens.refreshToken
        this.refreshExpiresIn = tokens.refreshExpiresIn
        Log.e("Refresh", "reg")
    }

    /**
     * Logout of the API
     *
     * @return bool: True if successful, False otherwise.
     */
    override suspend fun logout(): Boolean {
        if (this.isLoggedIn()) {
            val client = HttpClient(CIO) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
            val res: HttpResponse =
                client.post(this.logoutUrl) {
                    contentType(ContentType.Application.Json)
                    body = refreshToken
                }

            if (res.status.value > 299) {
                return false
            }

            this.tokenType = ""
            this.accessToken = ""
            this.expiresIn = 0
            this.refreshToken = "tokens.refreshToken"
            this.refreshExpiresIn = 0

            return true
        }
        // I feel like returning false here would imply they are still logged in
        return false
    }

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
    override suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        fields: MutableMap<String, String>,
        login: Boolean
    ): Map<String, String>? {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(): String {
        return "Bearer " + this.accessToken
    }

    override suspend fun getHeaders(): MutableMap<String, String> {
        if (this.headers.isEmpty()) {
            this.headers["Authorization"] = getToken()
        }

        return this.headers

    }

    override suspend fun isLoggedIn(): Boolean {
        if (this.accessToken == "") {
            return false
        }
        if (this.requiresRefresh()) {
            return false
        }

        return true
    }
}
