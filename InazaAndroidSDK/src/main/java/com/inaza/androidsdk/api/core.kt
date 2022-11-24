package com.inaza.androidsdk.api

import com.inaza.androidsdk.auth.Auth
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import java.io.File

/**
 * Api handler no auth
 *
 * @property orgId
 * @property region
 * @property url
 * @property timeout
 * @property asyncRequests
 * @constructor Create empty Api handler no auth
 */
open class ApiHandlerNoAuth(
    val orgId: String,
    val region: String? = null,
    var url: String? = null,
    open val timeout: Float = 10.0f,
    val asyncRequests: Boolean = true
) {
    private var orgUrl: String
    private var telematicsUrl: String
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    var orgData: OrgSettings? = null
    var orgDataRefreshing: Boolean = false
    private val regions = setOf("eu-1", "me-1", "us-1")

    init {
        if (this.url == null) {
            if (this.regions.contains(region)) {
                this.url = "https://${this.region}.nsurely-motor.com/v1/api"
            } else {
                // Raise exception
            }
            println("Raise value error")
        }
        this.telematicsUrl = "https://${this.region}.nsurely-motor.com/v1/telematics"
        this.orgUrl = "${this.url}/org/${this.orgId}"
    }

    /**
     * Refresh org data
     *
     */
    suspend fun refreshOrgData() {
        try {
            this.orgDataRefreshing = true

            val res = this.makeRequest(
                "GET",
                "",
                null,
                null,
                null,
                urlOverride = "${this.url}/public/${this.orgId}"
            )

            this.orgData = Json.decodeFromString<OrgSettings>(res.readText())

        } finally {
            this.orgDataRefreshing = true
        }
    }

    /**
     * Download file
     *
     * @param url
     */
    suspend fun HttpClient.downloadFile(url: String) {
        get<HttpStatement>(url).execute { response ->
            var offset = 0
            val byteArray = ByteArray(response.contentLength()!!.toInt())
            do {
                val currentRead = response.content.readAvailable(byteArray, offset, byteArray.size)
                offset += currentRead
            } while (currentRead > 0)
            File("C:\\Users\\ConorMcDonald\\Desktop\\file.txt").writeBytes(byteArray)
        }
    }

    /**
     * Download file
     *
     * @param url
     */
    suspend fun downloadFile(url: String) {
        this.client.downloadFile(url)
    }

    /**
     * Make request
     *
     * @param reqMet
     * @param endpoint
     * @param params
     * @param data
     * @param headers
     * @param timeout
     * @param urlOverride
     * @return
     */
    suspend fun makeRequest(
        reqMet: String,
        endpoint: String,
        params: MutableMap<String, String?>? = null,
        data: Any?,
        headers: MutableMap<String, String>? = null,
        timeout: Float = 10.0f,
        urlOverride: String? = null
    ): HttpResponse {
        val orgUrl = this.orgUrl
        var reqMethod: HttpMethod = HttpMethod.Get
        var url = urlOverride ?: "${orgUrl}/${endpoint}"
        when (reqMet) {
            "GET" -> reqMethod = HttpMethod.Get
            "POST" -> reqMethod = HttpMethod.Post
            "PATCH" -> reqMethod = HttpMethod.Patch
            "DELETE" -> reqMethod = HttpMethod.Delete
        }

        val res: HttpResponse =
            this.client.request(url) {
                contentType(ContentType.Application.Json)
                method = reqMethod
                if (headers != null) {
                    val keys = headers.keys
                    headers {
                        for (k in keys) {
                            headers[k]?.let { append(k, it) }
                        }
                    }
                }
                if (data != null) {
                    body = data
                }
                url {
                    if (params != null) {
                        for (p in params.keys) {
                            params[p]?.let { it1 -> parameters.append(p, it1) }
                        }
                    }

                }

            }

        if (res.status.value > 299) {
            throw Exception("Request failed with status ${res.status.value} : ${res.readText()}")
        }
        return res
    }

    private suspend fun loopRequest(
        method: String,
        url: String,
        params: Map<String, String>,
        data: Map<String, String>,
        headers: Map<String, String>
    ) {
        // Make an async req by default else a synchronous request
    }

    /**
     * Request
     *
     * @param method
     * @param endpoint
     * @param params
     * @param data
     * @param headers
     * @return
     */
    open suspend fun request(
        method: String,
        endpoint: String,
        params: MutableMap<String, String?>,
        data: Any,
        headers: Map<String, String>
    ): HttpResponse {

        val response = this.makeRequest(method, "", params, data, null)

        if (response.status.value !in 200..299) {
            println("Throw")
        }

        return response
    }

    /**
     * Close session
     *
     */
    suspend fun closeSession() {
        this.client.close()
    }

    /**
     * Telematics request
     *
     * @param method
     * @param endpoint
     * @param params
     * @param data
     * @param headers
     * @return
     */
    suspend fun telematicsRequest(
        method: String,
        endpoint: String,
        params: MutableMap<String, String?>? = null,
        data: Any? = null,
        headers: MutableMap<String, String>? = null
    ): String {
        val res = this.makeRequest(method, endpoint, params, data, headers)
        // Add model
        val body = res.readText()
        val status = res.status.value

        if (status < 300) {
            return body
        }
        return " Change the response model"
    }


}

/**
 * Api handler
 *
 * @property auth
 * @property timeout
 * @constructor
 *
 * @param orgId
 * @param region
 * @param url
 */
open class ApiHandler(
    orgId: String,
    val auth: Auth,
    region: String? = null,
    url: String?,
    override val timeout: Float = 10.0f
) : ApiHandlerNoAuth(orgId, region, url) {

    init {

    }

    /**
     * Make auth request
     *
     * @param method
     * @param path
     * @param params
     * @param data
     * @param _headers
     * @return
     */
    suspend fun makeAuthRequest(
        method: String,
        path: String,
        params: MutableMap<String, String?>? = null,
        data: Any? = null,
        _headers: MutableMap<String, String>? = null
    ): HttpResponse {
        var headers = _headers
        if (headers == null) {
            headers = mutableMapOf<String, String>()
        }
        headers["Authorization"] = auth.getToken()
        if (this.auth.requiresRefresh()) {
            println("refresh")
            this.auth.refresh()
        }
        // could call super method explicitly here...
//        super.makeRequest("POST","/auth/drivers/login",null,"",null)
        println("make req")
        return this.makeRequest(method, path, params, data, headers)
    }

    /**
     * Auth ok
     *
     * @return
     */
    suspend fun authOk(): Boolean {
        if (!this.auth.isLoggedIn()) {
            return false
        }
        if (this.auth.requiresRefresh()) {
            return false
        }

        return true
    }

    /**
     * Check auth
     *
     */
    suspend fun checkAuth() {
        if (!this.authOk()) {
            // Redundant checl??
            if (!this.auth.isLoggedIn()) {
                //TODO: Creds?
//                this.auth.login()
                return
            }

            if (this.auth.requiresRefresh()) {
                this.auth.refresh()
            }
        }
    }

//    override suspend fun request(method: String, endpoint: String, params: Map<String,String>, data: Map<String,String>, headers: Map<String,String>): HttpResponse {
//        this.checkAuth()
//        val response = this.makeAuthRequest(method, endpoint, params, data , headers)
//
//
//        if (response.status.value !in 200..299){
//            println("Throw")
//        }
//
//        return response
//
//    }


    /**
     * Batch fetch
     *
     * @param endpoint
     * @param params
     * @param headers
     * @param limit
     * @param offset
     * @return
     */
    suspend fun batchFetch(
        endpoint: String,
        params: MutableMap<String, String?>? = null,
        headers: MutableMap<String, String>? = null,
        limit: Int = 50,
        offset: Int = 0,
        maxRecords: Int? = null
    ): Flow<JsonElement> = flow {
        var offsetInt = offset
        val lim: Int = limit
        var p = params ?: mutableMapOf<String, String?>(
            "limit" to lim.toString(),
            "offset" to offsetInt.toString()
        )
        var count = 0
        while (true) {
            val response = this@ApiHandler.makeAuthRequest("GET", endpoint, p, null, headers)
            if (response.readText() == "[]" || response.readText() == "" || response == null) {
                // empty so break
                break
            }
//            println("Batch response ${response.readText()}")
            val resArray: JsonArray = Json.decodeFromString(response.readText())

            if (maxRecords != null) {
                for (res in resArray) {
                    if (count >= maxRecords) {
                        break
                    }
                    emit(res)
                    count++
                }
            } else {
                // fetch all
                for (res in resArray) {
                    emit(res)
                }

                // TODO will this trigger infinite loop if
                // maxRecords not specified and more than 50 records are returned?
                // listPolicies for example
                if (resArray.size < limit) {
                    break
                }
            }
            offsetInt += lim
            p["offset"] = offsetInt.toString()
        }
    }
}

