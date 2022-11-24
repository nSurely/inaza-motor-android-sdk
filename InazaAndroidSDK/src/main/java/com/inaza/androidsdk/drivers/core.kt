package com.inaza.androidsdk.drivers

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.checkApiHandler
import com.inaza.androidsdk.models.drivers.Driver
import com.inaza.androidsdk.search.Search
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


/**
 * Drivers
 *
 * @constructor
 *
 * @param orgId
 * @param auth
 * @param region
 * @param url
 */
class Drivers(var api: ApiHandler) {
    /**
     * Get a driver record
     *
     * @param driverId (str): the UUID of the driver.
     * @param risk (bool, optional): whether to include risk data. Defaults to True.
     * @param address  (bool, optional): whether to include address data. Defaults to True.
     * @param fleets (bool, optional): whether to include fleet data. Defaults to True.
     * @param vehicleCount  (bool, optional): whether to include vehicle count data. Defaults to False.
     * @param distance (bool, optional): whether to include distance data. Defaults to False.
     * @param points (bool, optional): whether to include points data. Defaults to True.
     * @param files (bool, optional): whether to include files data. Defaults to True.
     * @param contact (bool, optional): whether to include contact data. Defaults to True.
     * @param occupation (bool, optional): whether to include occupation data. Defaults to True.
     * @param query Mutable Map of strings. Additional query parameters
     * @return
     */
    suspend fun getDriver(
        driverId: String,
        risk: Boolean = true,
        address: Boolean = true,
        fleets: Boolean = true,
        vehicleCount: Boolean = false,
        distance: Boolean = false,
        points: Boolean = true,
        files: Boolean = true,
        contact: Boolean = true,
        occupation: Boolean = true,
        query: MutableMap<String, String>?
    ): Driver {
        checkApiHandler(this.api)
        val response: HttpResponse = this.api.makeAuthRequest("GET", "drivers/${driverId}")
        val d: Driver = Json.decodeFromString(response.readText())
        d.api = this.api
        return d
    }


    /**
     * List drivers
     *
     * @param dob
     * @param email
     * @param firstName
     * @param lastName
     * @param externalId
     * @param isActive
     * @param maxRecords
     * @return models.Driver
     */// TODO add in search type in args here
    suspend fun listDrivers(
        dob: Search? = null,
        email: Search? = null,
        firstName: String? = null,
        lastName: String? = null,
        externalId: String? = null,
        isActive: Boolean? = null,
        maxRecords: Int? = null
    ): Flow<Driver> = flow {
        checkApiHandler(this@Drivers.api)

        var params: MutableMap<String, String?> = mutableMapOf()

        if (dob != null) {
            params["dob"] = dob.toString()
        }

        if (email != null) {
            params["email"] = email.toString()
        }

        if (firstName != null) {
            params["firstName"] = firstName
        }

        if (lastName != null) {
            params["lastName"] = lastName
        }

        if (externalId != null) {
            params["externalId"] = externalId
        }

        if (isActive != null) {
            if (isActive) {
                params["isActive"] = "t"
            } else {
                params["isActive"] = "f"
            }
        }
        this@Drivers.api.batchFetch("drivers", params, null, maxRecords = maxRecords)
            .collect { res ->
                val driver: Driver = Json.decodeFromJsonElement(res)
                emit(driver)
            }
    }
}
