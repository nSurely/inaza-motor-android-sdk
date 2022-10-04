package auth

import io.github.cdimascio.dotenv.dotenv


/**
 * Auth type
 *
 * @constructor
 *
 * @param va
 */
enum class AuthType(va: Int) {
    /**
     * Api key
     *
     * @constructor Create empty Api key
     */
    apiKey(1),

    /**
     * Jwt driver
     *
     * @constructor Create empty Jwt driver
     */
    jwtDriver(2),

    /**
     * Jwt user
     *
     * @constructor Create empty Jwt user
     */
    jwtUser(3)
}


/**
 * Auth object for the API.
 * This object can handle JWT auth and API key auth, depending on the parameters passed
 *
 * @property apiKey API key, can be in format `publickey:secretkey` or just `pk` (must supply secret in this case). Defaults to None.
 * @property apiSecret API secret key. Must be supplied if api_key not in format `pk:sk`. Defaults to None.
 * @property email user/driver email. Defaults to None.
 * @property password user/driver password. Defaults to None.
 * @property accountType JWT account type ('user' or 'driver'). Defaults to None.
 * @constructor Create empty Auth
 */
class Auth(
    val apiKey: String? = null,
    val apiSecret: String? = null,
    val email: String? = null,
    val password: String? = null,
    val accountType: String? = null,
    var url: String? = null,
    val region: String? = null,
    orgId: String? = null
) : AuthBase() {
    private lateinit var authObj: AuthBase
    lateinit var authMethod: AuthType

    init {
        // url and orgId to be passed in Auth constructor?
        val regions = setOf("eu-1", "me-1", "us-1")

        if (this.url == null) {
            println("URL is null")
            if (regions.contains(region)) {
                this.url = "https://${this.region}.nsurely-motor.com/v1/api/org"
            } else {
                println("Raise value error")

            }
        }

        if (this.apiKey != null && this.apiSecret != null) {
            this.authMethod = AuthType.apiKey
            this.authObj = ApiKeyAuth(apiKey, apiSecret)
        } else if (this.email != null && this.password != null) {
            when (this.accountType) {
                "driver" -> {
                    this.authMethod = AuthType.jwtDriver

                    val dotenv = dotenv()
                    this.authObj = DriverAuth(
                        "${dotenv.get("DEV_URL")}/org/${dotenv.get("DEV_ORG")}",
                        dotenv.get("DEV_ORG"),
                        email,
                        password
                    )

                }
                "user" -> {
                    this.authMethod = AuthType.jwtUser
                    this.authObj = UserAuth("", "", email, password)
                }
                else -> {
                    throw AuthError("Invalid account type. Can only be 'driver' or 'user'.")
                }
            }
        } else {
            throw AuthError("Auth credentials are required.")
        }
    }

    /**
     * Requires refresh
     *
     * @return
     */
    override suspend fun requiresRefresh(): Boolean {
        return this.authObj.requiresRefresh()
    }

    /**
     * Refresh
     *
     */
    override suspend fun refresh() {
        this.authObj.refresh()
    }

    /**
     * Login
     *
     * @param email
     * @param password
     * @return
     */
    override suspend fun login(email: String, password: String): String {
        if (this.authMethod == AuthType.apiKey) {
            return ""
        }

        return this.authObj.login(email, password)

    }

    /**
     * Logout
     *
     * @return
     */
    override suspend fun logout(): Boolean {
        if (this.authMethod == AuthType.apiKey) {
            return true
        }
        // TODO FIX
        return true
    }

    /**
     * Get token
     *
     * @return
     */
    override suspend fun getToken(): String {
        return this.authObj.getToken()
    }

    /**
     * Get headers
     *
     * @return
     */
    override suspend fun getHeaders(): MutableMap<String, String> {
        return this.authObj.getHeaders()
    }

    /**
     * Is logged in
     *
     * @return
     */
    override suspend fun isLoggedIn(): Boolean {
        // TODO: ALWAYS TRUE?
        return this.isLoggedIn()
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
        if (this.authMethod == AuthType.apiKey) {
            return null
        }
        return this.authObj.signup(email, password, firstName, lastName, fields, login)
    }
}
