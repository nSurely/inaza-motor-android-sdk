package com.inaza.androidsdk.models.policy

abstract class PolicyNoClaimsBase {
    abstract val discountPc: Float
    abstract val forgiveness: Boolean
}

@kotlinx.serialization.Serializable
class PolicyNoClaims(
    override val discountPc: Float = 0.0f,
    override val forgiveness: Boolean = false
) : PolicyNoClaimsBase()