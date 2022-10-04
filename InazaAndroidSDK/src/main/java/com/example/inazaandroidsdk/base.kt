package com.example.inazaandroidsdk

import api.ApiHandler
import api.OrgSettings
import auth.Auth
import com.example.inazaandroidsdk.Exceptions.ValueException
import com.example.inazaandroidsdk.helpers.checkApiHandler
import com.example.inazaandroidsdk.models.drivers.CreateDriver
import drivers.Drivers
import fleets.Fleets
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.drivers.Driver
import models.fleets.Fleet
import com.example.inazaandroidsdk.models.vehicles.DriverVehicle
import com.example.inazaandroidsdk.models.vehicles.Vehicle
import search.Search
import vehicles.Vehicles


class Motor(
    val orgId: String,
    val region: String? = null,
    val auth: Auth?,
    val url: String? = null
) {

    lateinit var drivers: Drivers
    lateinit var fleets: Fleets
    lateinit var vehicles: Vehicles
    lateinit var api: ApiHandler

    init {
        if (this.auth != null) {
            this.api = ApiHandler(orgId, auth, region, url)
        }
//        } else {
//            this.api = ApiHandlerNoAuth(orgId, region, url)
//        }


        drivers = Drivers(this.api)
        vehicles = Vehicles(this.api)
        fleets = Fleets(this.api)

    }


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
        query: MutableMap<String, String>? = null
    ): Driver {

        return drivers.getDriver(
            driverId,
            risk,
            address,
            fleets,
            vehicleCount,
            distance,
            points,
            files,
            contact,
            occupation,
            query
        )
    }


//    suspend fun listDrivers(
//        dob: String? = null,
//        email: String? = null,
//        firstName: String? = null,
//        lastName: String? = null,
//        externalId: String? = null,
//        isActive: Boolean? = null,
//        maxRecords: Int? = null
//    ): Flow<Driver> = flow {
//        drivers.listDrivers(dob, email, firstName, lastName, externalId, isActive, maxRecords)
//            .collect { value ->
//                emit(value)
//            }
//    }

    suspend fun listDrivers(
        dob: Search? = null,
        email: Search? = null,
        firstName: String? = null,
        lastName: String? = null,
        externalId: String? = null,
        isActive: Boolean? = null,
        maxRecords: Int? = null
    ): Flow<Driver> = flow {
        drivers.listDrivers(
            dob,
            email,
            firstName,
            lastName,
            externalId,
            isActive,
            maxRecords
        ).collect { value ->
            emit(value)
        }
    }

    // TODO: Change response Model
    suspend fun getVehicle(
        vehicleId: String,
        includeTranslations: Boolean = true,
        includeDistance: Boolean = true,
        includeDrvCount: Boolean = false
    ): Vehicle {
        return vehicles.getVehicle(vehicleId, includeTranslations, includeDistance, includeDrvCount)
    }

    suspend fun listVehicles(regPlate: String? = null,
                             vin: String? = null,
                             isActive: Boolean? = null,
                             isApproved: Boolean? = null,
                             fullResponse: Boolean = true,
                             maxRecords: Int? = null
    ): Flow<Vehicle> = flow {
    vehicles.listVehicles(regPlate, vin, isActive, isApproved, fullResponse, maxRecords).collect {vehicle ->
        emit(vehicle)
    }

}
//    suspend fun searchVehicles(regPlate: String? = null, vin: String? = null, isActive: Boolean? = null, isApproved: Boolean? = null, fullResponse: Boolean = true){
//        return vehicles.searchVehicles(regPlate, vin, isActive, isApproved, fullResponse)
//    }

    suspend fun getFleet(fleetId: String, query: MutableMap<String, String?>? = null): Fleet? {
        return fleets.getFleet(fleetId, query)
    }

    suspend fun createFleet(newFleet: Fleet): Fleet{
        return fleets.createFleet(newFleet)
    }
    suspend fun listFleets(maxRecords: Int? = null): Flow<Fleet> = flow {
        fleets.listFleets(maxRecords).collect { fleet ->
            emit(fleet)
        }
    }

    suspend fun orgName(): String? {
        checkApiHandler(this.api)
        if (this.api.orgData != null){
            return this.api.orgData!!.displayName
        }
        withContext(Dispatchers.IO) { this@Motor.api.refreshOrgData() }
        return this.api.orgData!!.displayName
    }

    suspend fun orgSettings(): OrgSettings? {
        checkApiHandler(this.api)
        if (this.api.orgData == null){
            withContext(Dispatchers.IO) { this@Motor.api.refreshOrgData() }
        }

        return this.api.orgData
    }

    suspend fun language(): String? {
        checkApiHandler(this.api)
        if (this.api.orgData != null){
            return this.api.orgData!!.defaultLang
        }
        withContext(Dispatchers.IO) { this@Motor.api.refreshOrgData() }
        return this.api.orgData!!.defaultLang

    }

    /**
     * Create a new driver.
    If you would like to perform actions as this driver, you may need to login as the driver with a new motor and auth object.
    Note: SDK does not recommend using the JWT auth, so an API key should work fine as is.
     *
     * @param driver the driver model to create.
     * @param password the password for the driver, if invite is False.
     * @param sendInvite whether to send an invite email (password must be null if True).
     * @param sendWebhook whether to send a webhook.
     */
    suspend fun createDriver(driver: Driver, password: String? = null, sendInvite: Boolean = false, sendWebhook: Boolean = true): Driver {
        if (password == null && !sendInvite) {
            throw ValueException("You must provide a password if invite is False.")
        }

        if (password != null && sendInvite) {
            throw ValueException("You cannot provide a password if invite is True.")

        }

        val createDriver = Json.decodeFromString<CreateDriver>(Json.encodeToString(driver).removeSuffix("}").plus(", \"password\":\"$password\"}"))

        var webhook: String = "t"
        var invite: String = "f"

        if (!sendWebhook) {
            webhook = "f"
        }

        if (sendInvite){
            invite = "t"
        }

        val params = mutableMapOf<String, String?>("webhook" to webhook, "invite" to invite)
        val res = this.api.makeAuthRequest("POST", "drivers", data = createDriver, params = params)

        return Json.decodeFromString(res.readText())
    }

    /**
     * Create vehicle
     *
     * @param vehicle
     * @param driverId
     * @param drv
     * @param sendWebhook
     * @return
     */
    suspend fun createVehicle(vehicle: Vehicle, driverId: String? = null, drv: DriverVehicle? = null, sendWebhook: Boolean = true): Vehicle {
        return vehicles.createVehicle(vehicle, driverId, drv, sendWebhook)
    }

    /**
     * Request
     *
     * @param method the HTTP method.
     * @param path the path. The path is relative to the API base URL and org id, ie. <base url>/org/<org_id>/<<your path>>
     * @param data the data
     * @param params query string(s)
     * @param headers the headers
     */
    suspend fun request(method: String, path: String, data: MutableMap<String,Any?>, params: MutableMap<String,Any?>, headers: MutableMap<String,Any?>){
//        this.api.makeAuthRequest()
    }
}