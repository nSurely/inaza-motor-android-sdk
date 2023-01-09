package com.inaza.androidsdk.vehicles

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.models.vehicles.DriverVehicle
import com.inaza.androidsdk.models.vehicles.Vehicle
import com.inaza.androidsdk.models.vehicles.VehicleType
import com.inaza.androidsdk.search.Search
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


//data class getVehicleResponse()
class Vehicles(var api: ApiHandler) {


    suspend fun getVehicle(
        vehicleId: String,
        includeTranslations: Boolean = true,
        includeDistance: Boolean = true,
        includeDrvCount: Boolean = false
    ): Vehicle {

        var params = mutableMapOf<String, String?>()

        params["drv"] = "t"
        if (includeTranslations) {
            params["trns"] = "t"
        } else {
            params["trns"] = "f"
        }

        if (includeDistance) {
            params["distance3m"] = "t"
        } else {
            params["distance3m"] = "f"
        }

        if (includeDrvCount) {
            params["totalDrvCount"] = "t"
        } else {
            params["totalDrvCount"] = "f"
        }
        val response =
            this.api.makeAuthRequest("GET", "registered-vehicles/${vehicleId}", params, null, null)

        return Json.decodeFromString(response.readText())
    }

    suspend fun listVehicles(
        regPlate: String? = null,
        vin: String? = null,
        isActive: Boolean? = null,
        isApproved: Boolean? = null,
        fullResponse: Boolean = true,
        maxRecords: Int? = null
    ): Flow<Vehicle> = flow {

        val limit = 50
        var params = mutableMapOf<String, String?>()

        if (regPlate != null) {
            params["regPlate"] = regPlate
        }

        if (vin != null) {
            params["vin"] = vin
        }

        if (isActive != null) {
            params["isActive"] = isActive.toString()
        }

        if (isApproved != null) {
            params["isApproved"] = isApproved.toString()
        }

        if (maxRecords != null) {
            params["limit"] = maxRecords.toString()
        }
        val job = this@Vehicles.api.batchFetch(
            "registered-vehicles",
            params,
            null,
            limit = 20,
            maxRecords = maxRecords
        ).cancellable().collect { res ->
            val vehicle: Vehicle = Json.decodeFromJsonElement(res)
            vehicle.api = this@Vehicles.api
            emit(vehicle)
        }
    }

    suspend fun listVehicleTypes(
        brand: Search? = null,
        model: Search? = null,
        year: Int? = null,
        externalId: Search? = null,
        isActive: Boolean = true,
        maxRecords: Int? = null
    ): Flow<VehicleType> = flow{
        var params = mutableMapOf<String, String?>()
        if (brand != null) {
            params["brand"] = brand.toString()
        }
        if (model != null) {
            params["model"] = model.toString()
        }
        if (year != null) {
            params["year"] = year.toString()
        }
        if (externalId != null) {
            params["externalId"] = externalId.toString()
        }
        if (isActive) {
            params["isActive"] = isActive.toString()
        }

        val job = this@Vehicles.api.batchFetch(
            "vehicles",
            params,
            null,
            limit = 20,
            maxRecords = maxRecords
        ).cancellable().collect { res ->
            val vehicleType: VehicleType = Json.decodeFromJsonElement(res)
            emit(vehicleType)
        }
    }


    suspend fun createVehicle(
        vehicle: Vehicle,
        driverId: String? = null,
        drv: DriverVehicle? = null,
        sendWebhook: Boolean = true
    ): Vehicle {

        var wh: String = ""

        wh = if (sendWebhook) {
            "t"
        } else {
            "f"
        }
        val params = mutableMapOf<String, String?>("webhook" to wh)
        val rv: Vehicle = Json.decodeFromString(
            this.api.makeAuthRequest(
                "POST",
                "registered-vehicles",
                params,
                vehicle
            ).readText()
        )

        try {
            if (driverId != null) {
                if (drv == null) {
                    rv.addDriver(
                        driverId,
                        displayName = rv.getDisplay(),
                        isOwner = true,
                        isPrimaryDriver = true
                    )
                } else {
                    rv.addDrv(driverId, drv)
                }
            }
        } catch (e: Exception) {
            this.api.makeAuthRequest("DELETE", "registered-vehicles/${rv.id}", params, vehicle)
            // TODO throw exception
        }
        return rv
    }

    suspend fun createVehicleType(
        vehicleType: VehicleType,
        sendWebhook: Boolean = true
    ): VehicleType{
        var webhook = "t"
        if (!sendWebhook) {
            webhook = "f"
        }
        val res = this.api.makeAuthRequest("POST",
        "vehicles",
        data=vehicleType,
        params= mutableMapOf("webhook" to webhook)
        )

        return Json.decodeFromString(res.readText())
    }
//    suspend fun searchVehicles(regPlate: String? = null, vin: String? = null, isActive: Boolean? = null, isApproved: Boolean? = null, fullResponse: Boolean = true){
//        var params = mutableMapOf<String,String>()
//
//        if (regPlate != null){
//            params["regPlate"] = regPlate
//        }
//        if (vin != null){
//            params["vin"] = vin
//        }
//
//        if (isActive != null){
//            params["isActive"] = isActive.toString()
//        }
//
//        if (isApproved != null){
//            params["isApproved"] = isApproved.toString()
//        }
//
//        if (fullResponse){
//            params["full"] = "t"
//        } else {
//            params["full"] = "f"
//        }
//
//
//
//
//    }
}
