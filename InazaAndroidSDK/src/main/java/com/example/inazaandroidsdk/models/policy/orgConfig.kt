package models.policy

import models.policy.enums.PolicyConfigTemplate
import models.policy.enums.PolicyCoverType
import models.policy.enums.PolicyGroup

// TODO constrain ints
abstract class PolicyOrgConfigBase {
    abstract val group: PolicyGroup
    abstract val settings: PolicyConfigTemplate
    abstract val cover: List<PolicyCoverType>
    abstract val assignPolicyOnCreate: Boolean
    abstract val requiresApproval: Boolean
    abstract val validMins: Int?
    abstract val gracePeriodMins: Int
}

abstract class PolicyOrgConfigFlat : PolicyOrgConfigBase()

@kotlinx.serialization.Serializable
class PolicyOrgConfig(

    val config: PolicyConfig = PolicyConfig(),
    val contribution: PolicyContribution = PolicyContribution(),
    val excess: PolicyExcess = PolicyExcess(),
    val extras: PolicyExtrasNested = PolicyExtrasNested(),
    val fees: PolicyFees = PolicyFees(),
    val premium: PolicyBasePremium = PolicyBasePremium(),
    val rates: PolicyRates = PolicyRates(),
    val rewards: PolicyRewards = PolicyRewards(),
    // must constrain
    val telematics: PolicyTelematics = PolicyTelematics(),
    override val group: PolicyGroup,
    override val settings: PolicyConfigTemplate = PolicyConfigTemplate.custom,
    override val cover: List<PolicyCoverType> = listOf(PolicyCoverType.comprehensive),
    override val assignPolicyOnCreate: Boolean = false,
    override val requiresApproval: Boolean = false,
    override val validMins: Int? = null,
    override val gracePeriodMins: Int = 0
) : PolicyOrgConfigFlat()