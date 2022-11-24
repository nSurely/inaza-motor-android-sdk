package com.inaza.androidsdk.fleets

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.checkApiHandler
import com.inaza.androidsdk.models.fleets.Fleet
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Fleets
 *
 * @constructor
 *
 * @param orgId
 * @param auth
 * @param region
 * @param url
 */
class Fleets(var api: ApiHandler) {
    /**
     * Get fleet
     *
     * @param fleetId
     * @param query
     * @return
     */
    suspend fun getFleet(fleetId: String, query: MutableMap<String, String?>? = null): Fleet? {
        val response: HttpResponse? =
            this.api.makeAuthRequest("GET", "fleets/${fleetId}", params = query)

        val res: Fleet? = response?.let { Json.decodeFromString(it.readText()) }

        if (res != null) {
            res.api = api
        }

        return res
    }

    /**
     * List fleets
     *
     * @param maxRecords
     * @return
     */
    suspend fun listFleets(maxRecords: Int?): Flow<Fleet> = flow {
        checkApiHandler(this@Fleets.api)
        var count = 0

        this@Fleets.api.batchFetch("fleets", maxRecords = maxRecords).collect { res ->
            val fleet: Fleet = Json.decodeFromJsonElement(res)

            fleet.api = this@Fleets.api
            emit(fleet)
        }
    }


    /**
     * Create a new fleet.
     *
     * @param fleet the fleet model.
     */
    suspend fun createFleet(fleet: Fleet): Fleet {
        val res = this.api.makeAuthRequest("POST", "fleets", data = fleet)

        return Json.decodeFromString(res.readText())

    }

}
