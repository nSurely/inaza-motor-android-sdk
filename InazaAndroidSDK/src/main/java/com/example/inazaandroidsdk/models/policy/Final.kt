package models.policy

//TODO: Constrain floats
@kotlinx.serialization.Serializable
class PolicyFinalRates(
    val value: Float = 0.0f,
    val max: Float = 0.0f,
    val min: Float = 0.0f,
    val appliedRiskMultiplier: Float? = null
)

@kotlinx.serialization.Serializable
class PolicyFinalPremium(val value: Int = 0, val appliedRiskMultiplier: Float? = null)

abstract class PolicyFinalPricingBase {
    abstract val requiresReprice: Boolean
    abstract val rates: PolicyFinalRates
    abstract val premium: PolicyFinalPremium
}

@kotlinx.serialization.Serializable
class PolicyFinalPricing(
    override val requiresReprice: Boolean = false,
    override val rates: PolicyFinalRates = PolicyFinalRates(),
    override val premium: PolicyFinalPremium = PolicyFinalPremium()
) : PolicyFinalPricingBase()
