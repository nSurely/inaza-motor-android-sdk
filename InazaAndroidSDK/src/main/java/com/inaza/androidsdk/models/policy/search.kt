package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.models.policy.enums.PolicyGroup
import java.util.*

// frequent??
class PolicySearch(
    val id: String? = null,
    val display: String? = null,
    val approvedAt: Date? = null,
    val cancelledAt: Date? = null,
    val createdAt: Date? = null,
    val policyType: PolicyGroup? = null
)