package auth

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Create a new driver profile.
 *
 * @constructor
 *
 * @param url
 * @param orgId
 * @param email
 * @param password
 */
class DriverAuth(url: String, orgId: String, email: String, password: String) :
    JwtAuth(url, orgId, "driver", email, password) {

    override suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        fields: MutableMap<String, String>,
        login: Boolean
    ): Map<String, String>? {
        // if fields is null set to empty map
        // set login to true by default
        fields["email"] = email
        fields["password"] = password
        fields["firstName"] = firstName
        fields["lastName"] = lastName

        // TODO: tidy this up
        val client = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

        }
        // TODO check url here
        val headers = mutableMapOf<String, String>()
        val res: HttpResponse =
            client.request("/org/${orgId}/drivers") {
                contentType(ContentType.Application.Json)
                method = HttpMethod("POST")
                if (headers != null) {
                    val keys = headers.keys
                    headers {
                        for (k in keys) {
                            headers[k]?.let { append(k, it) }
                        }
                    }
                }
                body = fields

            }

        if (login) {
            this.login(email, password)
        }
        return fields

    }

}
