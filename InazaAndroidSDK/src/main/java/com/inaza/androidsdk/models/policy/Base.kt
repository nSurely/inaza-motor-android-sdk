package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.models.custom.PrivateApiHandler
import com.inaza.androidsdk.models.policy.enums.PolicyCoverType
import com.inaza.androidsdk.models.policy.enums.PolicyGroup
import java.time.OffsetDateTime


/**
 * The base policy model. All other models should be nested in a seprate model that inherits this one.
 *
 * @constructor Create empty Policy base
 */
abstract class PolicyBase : PrivateApiHandler() {
    abstract val canRenew: Boolean
    abstract val cover: List<PolicyCoverType>
    abstract val createdAt: OffsetDateTime?
    abstract var policyGroup: PolicyGroup?
    abstract val id: String?
    abstract val isActivePolicy: Boolean
    abstract val maxPassengers: Int

    // must constrain
    // TODO Int in the motorpy, should be double?
    abstract val sumInsured: Double
}

