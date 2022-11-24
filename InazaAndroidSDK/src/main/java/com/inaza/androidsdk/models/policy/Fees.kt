package com.inaza.androidsdk.models.policy

// constrain ints
abstract class PolicyFeesBase {
    abstract val cancellation: Int
    abstract val renewal: Int
    abstract val newBusiness: Int
}

@kotlinx.serialization.Serializable
class PolicyFees(
    override val cancellation: Int = 0,
    override val renewal: Int = 0,
    override val newBusiness: Int = 0
) : PolicyFeesBase()