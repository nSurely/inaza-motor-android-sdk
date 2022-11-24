package com.inaza.androidsdk.auth

class UserAuth(url: String, orgId: String, email: String, password: String) :
    JwtAuth(url, orgId, "user", email, password) {

    override suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        fields: MutableMap<String, String>,
        login: Boolean
    ): Map<String, String>? {
        throw AuthError("UserAuth.signup() is not implemented. A user must be added by another authenticated user.")
    }

    override suspend fun logout(): Boolean {
        return false
    }
}