package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.models.policy.enums.PaymentFrequency

// TODO constrain floats
abstract class PolicyRatesBase {
    abstract val enabled: Boolean
    abstract val value: Float
    abstract val max: Float
    abstract val min: Float
    abstract val chargeableDistanceKm: Int
    abstract val frequency: PaymentFrequency
    abstract val variable: Boolean
}

val frequency: PaymentFrequency = PaymentFrequency.bwl

@kotlinx.serialization.Serializable
class PolicyRates(
    override val enabled: Boolean = false,
    override val value: Float = 0.0f,
    override val max: Float = 0.0f,
    override val min: Float = 0.0f,
    override val chargeableDistanceKm: Int = 500,
    override val frequency: PaymentFrequency = PaymentFrequency.bwl,
    override val variable: Boolean = false
) : PolicyRatesBase()




