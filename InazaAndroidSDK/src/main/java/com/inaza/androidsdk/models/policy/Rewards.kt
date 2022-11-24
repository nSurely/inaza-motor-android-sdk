package com.inaza.androidsdk.models.policy

// TODO constrain
abstract class PolicyRewardsBase {
    abstract val enabled: Boolean
    abstract val maxMonthly: Int
    abstract val rates: PolicyRewardsRatesBase
    abstract val premium: PolicyRewardsPremiumBase
}

@kotlinx.serialization.Serializable
class PolicyRewards(
    override val enabled: Boolean = false,
    override val maxMonthly: Int = 0,
    override val rates: PolicyRewardsRatesBase = PolicyRewardsRatesBase(),
    override val premium: PolicyRewardsPremiumBase = PolicyRewardsPremiumBase()
) :
    PolicyRewardsBase()

// move + constrain
@kotlinx.serialization.Serializable
class PolicyRewardsPremiumBase(
    val enabled: Boolean = false,
    val maxDiscountPc: Float = 20.0f
)

// TODO constrain
// move to diff file
@kotlinx.serialization.Serializable
class PolicyRewardsRatesBase(
    val enabled: Boolean = false,
    val maxDiscountPc: Float = 20.0f
)

