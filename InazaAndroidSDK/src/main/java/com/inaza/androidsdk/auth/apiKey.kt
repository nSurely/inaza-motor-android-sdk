package com.inaza.androidsdk.auth

class ApiKeyAuth(private val key: String, private val secret: String) : AuthBase() {

    private val headers = mutableMapOf<String, String>()

    init {
        if (key == "") {
            throw ApiKeyAuthError("API key is required")
        }

        if (secret == "") {
            throw ApiKeyAuthError("Api secret is required")
        }
    }

    override suspend fun requiresRefresh(): Boolean {
        return false
    }

    override suspend fun refresh() {}

    override suspend fun getToken(): String {
        return "apiKey " + "${this.key}:${this.secret}"
    }

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

    override suspend fun getHeaders(): MutableMap<String, String> {
        if (this.headers.isEmpty()) {
            this.headers["Authorization"] = "apiKey ${getToken()}"
        }

        return this.headers
    }

    override suspend fun isLoggedIn(): Boolean {
        return true
    }

    override suspend fun login(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Boolean {
        TODO("Not yet implemented")
    }
}
