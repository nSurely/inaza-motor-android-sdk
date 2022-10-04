package com.example.inazaandroidsdk.models.drivers

import api.ApiHandler
import helpers.KOffsetDateTimeSerializer
import io.ktor.client.statement.*
import kotlinx.datetime.LocalDate
import kotlinx.serialization.*
import models.custom.PrivateApiHandler
import models.drivers.Occupation
import models.fleets.Fleet
import models.risk.Risk
import java.time.OffsetDateTime


/**
 * The Driver object, representing a driver in the API.
 * Billing accounts, vehicles and more can be accessed via the Driver object.
 *
 * @property api
 * @property id
 * @property sourceId
 * @property externalId
 * @property firstName
 * @property middleName
 * @property lastName
 * @property gender
 * @property email
 * @property dob
 * @property phone
 * @property lang
 * @property drivingStartDate
 * @property occupation
 * @property apiPath
 * @property adrLine1
 * @property adrLine2
 * @property adrLine3
 * @property county
 * @property province
 * @property postcode
 * @property countryIsoCode
 * @property countryName
 * @property approvedAt
 * @property activationId
 * @property driverActivated
 * @property activatedAt
 * @property isApproved
 * @property isActive
 * @property driversLicenseLoc
 * @property proofOfAddressLoc
 * @property idLoc
 * @property profilePicLoc
 * @property vehicleCount
 * @property totalPoints
 * @property distanceKm30Days
 * @property createdAt
 * @property fleets
 * @property lastLogin
 * @property appLogin
 * @property appVersion
 * @property appDownloaded
 * @property risk
 * @constructor Create empty Driver
 */
@Serializable
class CreateDriver(
    @Transient
    override var api: ApiHandler? = null,

    @SerialName("id")
    private val _id: String? = null,

    @SerialName("sourceId")
    private val _sourceId: String? = null,

    @SerialName("externalId")
    private val _externalId: String? = null,

    @SerialName("firstName")
    private val _firstName: String,

    @SerialName("middleName")
    private val _middleName: String? = null,

    @SerialName("lastName")
    private val _lastName: String,

    @SerialName("gender")
    private val _gender: String? = null,

    @SerialName("email")
    private val _email: String? = null,

    @SerialName("dob")
    private val _dob: LocalDate? = null,

    @SerialName("telE164")
    private val _phone: String? = null,

    @SerialName("lang")
    private val _lang: String? = null,

    @SerialName("drivingStartDate")
    private val _drivingStartDate: LocalDate? = null,

    @SerialName("occupation")
    private val _occupation: Occupation? = null,

    @SerialName("apiPath")
    private val _apiPath: String? = null,

    @SerialName("adrLine1")
    private val _adrLine1: String? = null,

    @SerialName("adrLine2")
    private val _adrLine2: String? = null,

    @SerialName("adrLine3")
    private val _adrLine3: String? = null,

    @SerialName("county")
    private val _county: String? = null,

    @SerialName("province")
    private val _province: String? = null,

    @SerialName("postcode")
    private val _postcode: String? = null,

    @SerialName("countryIsoCode")
    private val _countryIsoCode: String? = null,

    @SerialName("countryName")
    private val _countryName: String? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("approvedAt")
    private val _approvedAt: OffsetDateTime? = null,

    @SerialName("activationId")
    private val _activationId: String? = null,

    @SerialName("driverActivated")
    private val _driverActivated: Boolean? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("activatedAt")
    private val _activatedAt: OffsetDateTime? = null,

    @SerialName("isApproved")
    private val _isApproved: Boolean = false,

    @SerialName("isActive")
    private val _isActive: Boolean = false,

    @SerialName("driversLicenseLoc")
    private val _driversLicenseLoc: String? = null,

    @SerialName("proofOfAddressLoc")
    private val _proofOfAddressLoc: String? = null,

    @SerialName("idLoc")
    private val _idLoc: String? = null,

    @SerialName("profilePicLoc")
    private val _profilePicLoc: String? = null,

    @SerialName("vehicleCount")
    private val _vehicleCount: Int = 0,

    // meta
    @SerialName("totalPoints")
    private val _totalPoints: Int? = 0,

    @SerialName("distanceKm30Days")
    private val _distanceKm30Days: Int? = null,


    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("createdAt")
    private val _createdAt: OffsetDateTime? = null,

    @SerialName("fleets")
    private val _fleets: List<Fleet>? = null,
    // Try to inherit from CommonRisk

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("lastLogin")
    private val _lastLogin: OffsetDateTime? = null,

    @SerialName("appLogin")
    private val _appLogin: Boolean? = null,

    @SerialName("appVersion")
    private val _appVersion: String? = null,

    @SerialName("appDownloaded")
    private val _appDownloaded: Boolean? = null,

    @SerialName("risk")
    private val _risk: Risk? = null,

    private val password: String
) : PrivateApiHandler() {
   }