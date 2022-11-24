package com.inaza.androidsdk.models.policy


// Make an enum
//Todo: constrain
abstract class PolicyTelematicsBase {
    //    If 'indefinite', then telematics is run indefinitely.
//If 'days', then telematics is run for a specific number of days.
//If 'min_km', then telematics is run for a specific number of km.
    abstract val process: String
    abstract val days: Int?
    abstract val minKm: Int?
}

@kotlinx.serialization.Serializable
class PolicyTelematics(
    override val process: String = "indefinite",
    override val days: Int? = null,
    override val minKm: Int? = null
) : PolicyTelematicsBase()