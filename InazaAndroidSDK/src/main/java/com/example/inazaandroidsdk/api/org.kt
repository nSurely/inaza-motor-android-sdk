package api

data class Design(
    val primaryHex: String,
    val secondaryHex: String,
    val tertiaryHex: String,
    val fontFamilyPrimary: String,
    val fontFamilySecondary: String,
    val fontPrimaryHex: String,
    val fontSecondaryHex: String,
    val primaryLogoUrl: String,
    val secondaryLogoUrl: String,
    val primaryLogoUrlSmall: String,
    val secondaryLogoUrlSmall: String
)

data class Policy(
    val policyDrvOn: Boolean,
    val policyRvOn: Boolean,
    val policyDriverOn: Boolean,
    val policyFleetOn: Boolean
)

data class Features(
    val claimsOn: Boolean,
    val emergenciesOn: Boolean,
    val rewardsOn: Boolean,
    val scoringOn: Boolean,
    val scoringLeaderboardOn: Boolean,
    val billingOn: Boolean,
    val fleetOn: Boolean,
    val fleetBillingOn: Boolean
)

data class App(
    val autoTrackingOn: Boolean,
    val uiLayout: Boolean,
    val showTripsOn: Boolean,
    val signupOn: Boolean
)

data class Telematics(
    val autoTracking: MutableMap<String, Any>,
    val dataCapture: MutableMap<String, Any>
)

data class Tos(
    val contactEmail: String,
    val requirePrivacyAgreement: Boolean,
    val privacyPolicyDisplay: String,
    val privacyPolicyUrl: String,
    val privacyPolicyHtml: String,
    val requireTosAgreement: Boolean,
    val tosDisplay: String,
    val tosUrl: String,
    val tosHtml: String,
    val DPOEmail: String
)

data class Config(
    val defaultLang: String,
    val requireProofOfAddress: Boolean,
    val requireId: Boolean,
    val requireSelfie: Boolean,
    val requireVehiclePicFull: Boolean,
    val requireVehiclePicSingle: Boolean,
    val requireVehicleProofOfReg: Boolean,
    val requireDriversLicense: Boolean,
    val driverApproval: Boolean,
    val vehicleApproval: Boolean,
    val useVehicleRegistry: Boolean
)

data class OrgSettings(
    val profileType: String? = null,
    val sourceIdType: String? = null,
    val design: Design? = null,
    val policy: Policy? = null,
    val features: Features? = null,
    val app: App? = null,
    val telematics: Telematics? = null,
    val externalId: String? = null,
    val displayName: String? = null,
    val env: String? = null,
    val isActive: Boolean? = null,
    val id: String? = null,
    val createdAt: String? = null,
    val orgGroupId: String? = null,
    val orgGroupDisplayName: String? = null,
    val defaultLang: String? = null,
    val tos: Tos? = null,
    val config: Config? = null
)