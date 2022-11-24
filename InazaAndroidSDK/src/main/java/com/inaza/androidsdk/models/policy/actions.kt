package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.models.policy.enums.PolicyGroup

suspend fun createPolicy(
    api: ApiHandler,
    recordId: String,
    group: PolicyGroup,
    policy: Policy? = null,
    driverId: String? = null,
    vehicleId: String? = null
) {
    var policy = policy
    if (policy == null) {
        policy = Policy()
    }

    policy.policyGroup = group
    var recordId = recordId
    var params = mutableMapOf<String, Any>()
    if (group == PolicyGroup.d) {
        if (driverId != null) {
            if (driverId != recordId) {
            }
        }
    } else if (group == PolicyGroup.drv) {

    } else if (group == PolicyGroup.rv) {

    } else if (group == PolicyGroup.fd) {
        if (driverId == null) {
            throw java.lang.Exception("driver_id must be supplied for FD policies")
        } else if (driverId == recordId) {
            throw java.lang.Exception("record_id (fleet id) must not be the same as driver_id for FD policies")
        }
    } else if (group == PolicyGroup.fdrv) {
        if (driverId == null) {
            throw java.lang.Exception("driver_id must be supplied for FDRV policies")
        } else if (driverId == recordId) {
            throw java.lang.Exception("record_id (fleet id) must not be the same as driver_id for FDRV policies")
        }
    } else if (group == PolicyGroup.frv) {
        if (vehicleId == null) {
            throw java.lang.Exception("vehicleID must be supplied for FDRV policies")
        } else if (driverId == recordId) {
            throw java.lang.Exception("record_id (fleet id) must not be the same as driver_id for FDRV policies")
        }
    }
}